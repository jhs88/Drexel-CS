import os
import re
import sqlite3


SQLITE_PATH = os.path.join(os.path.dirname(__file__), 'workhard.db')


class Database:

    def __init__(self):
        self.conn = sqlite3.connect(SQLITE_PATH)

    def select(self, sql, parameters=[]):
        c = self.conn.cursor()
        c.execute(sql, parameters)
        return c.fetchall()

    def execute(self, sql, parameters=[]):
        c = self.conn.cursor()
        c.execute(sql, parameters)
        self.conn.commit()

    def get_user_food(self, user_id, week):
        data = self.select(
            'SELECT * FROM food WHERE user_id=? AND week=? ORDER BY date ASC', [user_id, week])
        return [{
            'uid': d[0],
            'name': d[1],
            'day': d[2],
            'week':d[3],
            'type': d[4],
            'desc': d[5],
            'calories': d[6],
            'user_id': d[7]
        } for d in data]

    def get_user_week_calories(self, user_id, week):
        data = self.select(
            'SELECT * FROM food WHERE user_id=? AND week=?', [user_id, week])
        return [{
            'day': d[2],
            'calories': d[6]
        } for d in data]

    def create_food(self, name, day, week, type, desc, calories, user_id):
        self.execute('INSERT INTO food (name, day, week, type, desc, calories, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)',
                     [name, day, week, type, desc, calories, user_id])

    def create_user(self, name, username, encrypted_password):
        self.execute('INSERT INTO users (name, username, encrypted_password) VALUES (?, ?, ?)',
                     [name, username, encrypted_password])

    def get_user(self, username):
        data = self.select('SELECT * FROM users WHERE username=?', [username])
        if data:
            d = data[0]
            return {
                'name': d[0],
                'username': d[1],
                'encrypted_password': d[2],
                'user_id': d[3]
            }
        else:
            return None

    def close(self):
        self.conn.close()
