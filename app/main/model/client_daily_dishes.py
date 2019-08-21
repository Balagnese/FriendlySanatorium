from .. import db
from flask_admin.contrib.sqla import ModelView


class ClientSelectedDish(db.Model):
    __tablename__ = 'client_selected_dishes'

    client_id = db.Column(db.Integer, db.ForeignKey('client_profile.id'), primary_key=True)
    client = db.relationship("ClientProfile", uselist=False)
    meal_id = db.Column(db.Integer, db.ForeignKey('meal.id'), primary_key=True)
    meal = db.relationship('Meal', uselist=False)
    dishes_group_id = db.Column(db.Integer, db.ForeignKey('dishes_group.id'), primary_key=True)
    dishes_group = db.relationship('DishesGroup', uselist=False)
    dish_id = db.Column(db.Integer, db.ForeignKey('dish.id'), primary_key=True)
    dish = db.relationship('Dish')
