from flask import Flask, render_template, send_file, g, request, jsonify, session, escape, redirect
from passlib.hash import pbkdf2_sha256
import os
from db import Database

app = Flask(__name__, static_folder='public', static_url_path='')
app.secret_key = b'lkj98t&%$3rhfSwu3D'

def get_db():
    db = getattr(g, '_database', None)
    if db is None:
        db = Database()
    return db

@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/about')
def about():
    return render_template('about.html')

@app.route('/food')
def chart():
    return render_template('food.html')

@app.route('/next')
def next():
    session['week'] = session.get('week') + 1
    return redirect('/food')

@app.route('/back')
def back():
    if session['week'] == 1:
        return redirect('/food')
    else:
        session['week'] = session.get('week') - 1
        return redirect('/food')

@app.route('/api/myfood')
def api_myfood():
    if 'user' in session:
        user_id = session['user']['user_id']
        week = session['week'] 
        response = get_db().get_user_week_calories(user_id, week)
        return jsonify(response)
    else:
        return jsonify('Error: User not authenticated')

@app.route('/create_food', methods=['GET', 'POST'])
def create_food():
    if request.method == 'POST':
        name = request.form['name']
        day = request.form['day']
        week = session['week']
        type = request.form['type']
        desc = request.form['desc']
        calories = request.form['calories']
        user_id = session['user']['user_id']
        get_db().create_food(name, day, week, type, desc, calories, user_id)
        return redirect('/food')
    return render_template('create_food.html')


@app.route('/create_user', methods=['GET', 'POST'])
def create_user():
    if request.method == 'POST':
        name = request.form['name']
        username = request.form['username']
        typed_password = request.form['password']
        if name and username and typed_password:
            encrypted_password = pbkdf2_sha256.encrypt(typed_password, rounds=200000, salt_size=16)
            get_db().create_user(name, username, encrypted_password)
            return redirect('/login')
    return render_template('create_user.html')


@app.route('/login', methods=['GET', 'POST'])
def login():
    message = None
    if request.method == 'POST':
        username = request.form['username']
        typed_password = request.form['password']
        if username and typed_password:
            user = get_db().get_user(username)
            if user:
                if pbkdf2_sha256.verify(typed_password, user['encrypted_password']):
                    session['user'] = user
                    session['week'] = 1
                    return redirect('/food')
                else:
                    message = "Incorrect password, please try again"
            else:
                message = "Unknown user, please try again"
        elif username and not typed_password:
            message = "Missing password, please try again"
        elif not username and typed_password:
            message = "Missing username, please try again"
    return render_template('login.html', message=message)


@app.route('/logout')
def logout():
    session.pop('user', None)
    session.pop('week', None)
    return redirect('/')


@app.route('/<name>')
def generic(name):
    if 'user' in session:
        return render_template(name + '.html')
    else:
        return redirect('/login')


if __name__ == "__main__":
    session['week'] = 1 # setting session data
    app.run(host='127.0.0.1', port=8080, debug=True)
