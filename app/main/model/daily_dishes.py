from .. import db
# from sqlalchemy.dialects.postgresql import JSON
from flask_admin.contrib.sqla import ModelView


dishes_groups_to_meals = db.Table('dishes_groups_to_meal_dishes',
                                        db.Column('meal_id', db.Integer, db.ForeignKey('meal.id')),
                                        db.Column('dishes_group_id', db.Integer, db.ForeignKey('dishes_group.id'))
                                        )


dishes_to_dishes_groups = db.Table('dishes_to_dishes_groups',
                                   db.Column('dish_id', db.Integer, db.ForeignKey('dish.id')),
                                   db.Column('dishes_group_id', db.Integer, db.ForeignKey('dishes_group.id'))
                                   )


class Dish(db.Model):
    __tablename__ = 'dish'
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    name = db.Column(db.String)
    description = db.Column(db.String)

    def __repr__(self):
        return "<{}: {}>".format(self.id, self.name)


class DishesGroup(db.Model):
    __tablename__ = 'dishes_group'

    id = db.Column(db.Integer, autoincrement=True, primary_key=True)
    name = db.Column(db.String, nullable=False)
    dishes = db.relationship("Dish", secondary=dishes_to_dishes_groups)

    def __repr__(self):
        return "<{}: {}, total:{}>".format(self.id, self.name, len(self.dishes))


class Meal(db.Model):
    __tablename__ = 'meal'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)

    # daily_menu_id = db.Column(db.Integer, db.ForeignKey('daily_menu.id'), nullable=False)
    # daily_menu = db.relationship('DailyMenu', db.ForeignKey('daily_menu.id'))

    dishes_lists = db.relationship('DishesGroup', secondary=dishes_groups_to_meals)


class DailyMenu(db.Model):
    __tablename__ = 'daily_menu'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)

    diet_id = db.Column(db.Integer, db.ForeignKey('diet.id'))
    diet = db.relationship('Diet')

    date = db.Column(db.Date, nullable=False)

    breakfast_id = db.Column(db.Integer, db.ForeignKey('meal.id'), nullable=False)
    lunch_id = db.Column(db.Integer, db.ForeignKey('meal.id'), nullable=False)
    supper_id = db.Column(db.Integer, db.ForeignKey('meal.id'), nullable=False)

    breakfast = db.relationship('Meal', foreign_keys=[breakfast_id])
    lunch = db.relationship('Meal', foreign_keys=[lunch_id])
    supper = db.relationship('Meal', foreign_keys=[supper_id])


class AdminModelDish(ModelView):
    pass

class AdminModelDishesGroup(ModelView):
    pass

# class Admin
