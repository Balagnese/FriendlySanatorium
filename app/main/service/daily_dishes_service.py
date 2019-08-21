from app.main import db
from ..model.daily_dishes import Dish, DishesGroup, Meal, DailyMenu

from ..util.exceptions import EntityNotFoundException
from .diet_service import get_a_diet


def save_dish(name, description):
    dish = Dish(name=name, description=description)
    db.session.add(dish)
    db.session.commit()

    return {
               'status':  'success',
               'message': 'Dish successfully created'
           }, 201


def get_a_dish(dish_id):
    return Dish.query.filter_by(id=dish_id).first()


def get_all_dishes():
    return Dish.query.all()


def save_dishes_group(name):
    dishes_group = DishesGroup(name=name)
    db.session.add(dishes_group)
    db.session.commit()

    return {
               'status':  'success',
               'message': 'DishesGroup successfully created'
           }, 201


def get_a_dishes_group(dishes_group_id):
    return DishesGroup.query.filter_by(id=dishes_group_id).first()


def get_all_dishes_groups():
    return DishesGroup.query.all()

def _save_daily_menu_and_return(diet, date):
    breakfast = Meal()
    lunch = Meal()
    supper = Meal()

    daily_menu = DailyMenu(diet=diet, date=date,
                           breakfast=breakfast,
                           lunch=lunch,
                           supper=supper)

    db.session.add(daily_menu)
    db.session.commit()
    return daily_menu

def save_daily_menu(diet_id, date):
    diet = get_a_diet(diet_id)
    if not diet:
        raise EntityNotFoundException('diet')

    _save_daily_menu_and_return(diet, date)
    return {
        'status': 'success',
        'message': 'DailyMenu successfully created'
    }, 201


def get_a_meal(meal_id):
    return Meal.query.filter_by(id=meal_id).first()


def get_a_daily_menu(daily_menu_id):
    return DailyMenu.query.filter_by(id=daily_menu_id).first()


def get_all_daily_menu():
    return DailyMenu.query.all()


def get_a_daily_menu_by_day(date, diet):
    menu = DailyMenu.query.filter_by(date=date, diet=diet).first()
    if not menu:
        menu = _save_daily_menu_and_return(diet, date)

    return menu


def add_dishes_to_dishes_group(dishes_ids, dishes_group_id):
    dishes = Dish.query.filter(Dish.id.in_(dishes_ids)).all()
    if not dishes:
        raise EntityNotFoundException('dish')

    dishes_group:DishesGroup = get_a_dishes_group(dishes_group_id)
    if not dishes_group:
        raise EntityNotFoundException('dishes_group')

    for dish in dishes:
        if dishes_group.dishes.count(dish) == 0:
            dishes_group.dishes.append(dish)

    db.session.add(dishes_group)
    db.session.commit()

    return {
        'status': 'success',
        'message': 'dish successfully added to dishes group'
    }, 200


def add_dishes_group_to_meal(dishes_groups_ids, daily_menu_id, meal_tag):

    daily_menu = get_a_daily_menu(daily_menu_id)
    if not daily_menu:
        raise EntityNotFoundException('daily_menu')

    if meal_tag == 'breakfast':
        meal = daily_menu.breakfast
    elif meal_tag == 'lunch':
        meal = daily_menu.lunch
    else:
        meal = daily_menu.supper

    for dishes_group_id in dishes_groups_ids:
        dishes_group = get_a_dishes_group(dishes_group_id)
        if dishes_group is None:
            continue
        if meal.dishes_lists.count(dishes_group) == 0:
            meal.dishes_lists.append(dishes_group)

    db.session.add(meal)
    db.session.commit()

    return {
        'status': 'success',
        'message': 'dishes_group_successfully added to meal'
    }, 200


def meal_contains_dishes_group(meal, dishes_group):
    return meal.dishes_lists.count(dishes_group) != 0


def dishes_group_contains_dish(dishes_group, dish):
    return dishes_group.dishes.count(dish) != 0


def get_a_daily_menu_by(diet, date):
    return DailyMenu.query.filter_by(diet=diet, date=date).first()