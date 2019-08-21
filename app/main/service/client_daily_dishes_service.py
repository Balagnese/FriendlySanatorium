from app.main import db
from ..model.client_daily_dishes import ClientSelectedDish
from ..model.daily_dishes import DailyMenu

from ..util.exceptions import EntityNotFoundException

from .daily_dishes_service import  get_a_dishes_group, get_a_dish, get_a_daily_menu, \
    meal_contains_dishes_group, dishes_group_contains_dish


# def encode_daily_menu(daily_menu: DailyMenu):
#
#     def encode_meal(menu_id, meal_tag, meal: Meal):
#         dishes_lists = []
#         for _dishes_group in meal.dishes_lists:
#             dishes_group: DishesGroup = _dishes_group
#             dishes_list = [str(menu_id)+ '_' +str(meal_tag) + '_' + str(dishes_group.id)+ '_' +str(dish.id) for dish in dishes_group.dishes]
#             dishes_lists.append(dishes_list)
#         return dishes_lists
#     res = {
#
#     }
#     daily_menu

def get_a_client_selected_dish(client, meal, dishes_group, dish):
    return ClientSelectedDish.query.filter_by(client=client,
                                              meal=meal,
                                              dishes_group=dishes_group,
                                              dish=dish).first()


def remove_a_client_selected_dish(client, daily_menu_id, meal_tag, dishes_group_id, dish_id):
    daily_menu = get_a_daily_menu(daily_menu_id)
    if daily_menu.diet_id != client.diet_id:
        return {
                   'status':  'fail',
                   'message': 'Invalid day_menu_id. client\'s diet differ with passed day menu\'s diet'
               }, 422

    dishes_group = get_a_dishes_group(dishes_group_id)
    if not dishes_group:
        raise EntityNotFoundException('dishes_group')

    dish = get_a_dish(dish_id)
    if not dish:
        raise EntityNotFoundException('dish')

    if meal_tag == 'breakfast':
        meal = daily_menu.breakfast
    elif meal_tag == 'lunch':
        meal = daily_menu.lunch
    else:
        meal = daily_menu.supper

    client_selected_dish = get_a_client_selected_dish(client, meal, dishes_group, dish)
    if not client_selected_dish:
        raise EntityNotFoundException('client_selected_dish')

    db.session.delete(client_selected_dish)
    db.session.commit()
    return {
        'status': 'success',
        'message': 'client_selected_dish successfully deleted'
    }, 200


def save_client_selected_dish(client, daily_menu_id, meal_tag, dishes_group_id, dish_id):
    daily_menu = get_a_daily_menu(daily_menu_id)
    if daily_menu.diet_id != client.diet_id:
        return {
            'status': 'fail',
            'message': 'Invalid day_menu_id. client\'s diet differ with passed day menu\'s diet'
        }, 422

    dishes_group = get_a_dishes_group(dishes_group_id)
    if not dishes_group:
        raise EntityNotFoundException('dishes_group')

    dish = get_a_dish(dish_id)
    if not dish:
        raise EntityNotFoundException('dish')

    if meal_tag == 'breakfast':
        meal = daily_menu.breakfast
    elif meal_tag == 'lunch':
        meal = daily_menu.lunch
    else:
        meal = daily_menu.supper

    if not meal_contains_dishes_group(meal, dishes_group):
        return {
                   'status':  'fail',
                   'message': 'DishesGroup does not belong to Meal({})'.format(meal_tag)
               }, 422

    if not dishes_group_contains_dish(dishes_group, dish):
        return {
                   'status':  'fail',
                   'message': 'Dish does not belong to DishesGroup'
               }, 422

    if ClientSelectedDish.query.filter_by(client=client,
                                          meal=meal,
                                          dishes_group=dishes_group,
                                          dish=dish).first() is not None:
        return {
            'status': 'fail',
            'message': 'selection already been made'
        }, 422

    client_selected = ClientSelectedDish(client=client, meal=meal, dishes_group=dishes_group, dish=dish)
    db.session.add(client_selected)
    db.session.commit()

    return {
        'status': 'success',
        'message': 'dish successfully selected'
    }, 200


def get_client_selected_dishes_grouped(client, date):
    daily_menu = DailyMenu.query.filter_by(date=date, diet=client.diet).first()
    if not daily_menu:
        raise EntityNotFoundException('daily_menu', 'not found daily menu for given date matching client diet')

    selected_breakfast = []#ClientSelectedDish.query.filter_by(client=client, meal=daily_menu.breakfast).all()
    not_selected_breakfast = []
    selected_lunch = []#ClientSelectedDish.query.filter_by(client=client, meal=daily_menu.lunch).all()
    not_selected_lunch = []
    selected_supper = []#ClientSelectedDish.query.filter_by(client=client, meal=daily_menu.supper).all()
    not_selected_supper = []

    for meal, selected, not_selected in ((daily_menu.breakfast, selected_breakfast, not_selected_breakfast),
                                         (daily_menu.lunch, selected_lunch, not_selected_lunch),
                                         (daily_menu.supper, selected_supper, not_selected_supper)):
        for group in meal.dishes_lists:
            selected_dish_from_group = ClientSelectedDish.query.filter_by(client=client, meal=meal,
                                                                          dishes_group=group).first()
            if not selected_dish_from_group:
                not_selected.append(group)
            else:
                selected.append({'dishes_group': group, 'dish': selected_dish_from_group.dish})


    return {
        'daily_menu_id': daily_menu.id,
        'breakfast': {
            'selected': selected_breakfast,
            'not_selected': not_selected_breakfast
        },
        'lunch': {
            'selected': selected_lunch,
            'not_selected': not_selected_lunch
        },
        'supper': {
            'selected': selected_supper,
            'not_selected': not_selected_supper
        }
    }
