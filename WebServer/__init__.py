import random

from flask import Flask, request

import uuid

app = Flask(__name__)

games = []

def check_collisions(code):
    for game in games:
        if game["code"] == code:
            return True
    return False

def check_creds(auth_token, game_code):
    for game in games:
        if game["code"] == game_code and auth_token == game["auth_token"]:
            return True
    return False

def set_turn(game_code, turn):
    for game in games:
        if game["code"] == game_code:
            game["turn"] = turn

def get_board(game_code):
    for game in games:
        if game["code"] == game_code:
            return game["board"]
    return None

def get_turn(game_code):
    for game in games:
        if game["code"] == game_code:
            return game["turn"]

def set_board(game_code, board):
    for game in games:
        if game["code"] == game_code:
            game["board"] = board

@app.route("/")
def main():
    return {"staus" : "online"}

@app.route("/newgame")
def newGame():
    game_code = random.randint(1000,9999)

    break_out = 0
    while check_collisions(game_code):
        break_out += 1
        game_code = random.randint(1000,9999)
        if break_out == 10:
            return "Internal Server Error", 500

    auth_token = uuid.uuid4()

    new_game = {"code" : game_code, "auth_token" : auth_token, "board": None, "turn": None}
    games.append(new_game)
    return new_game

@app.route("/getturn")
def getturn():
    game_code = request.form.get('code')
    return get_turn(game_code)
@app.route("/setturn", methods=['POST'])
def setturn():
    game_code = request.form.get('code')
    auth_token = request.form.get('auth_token')
    turn = request.form.get('turn')
    if turn != "red" and turn != "blue":
        return "Invalid Turn", 400

    if check_creds(auth_token, game_code):
        set_turn(game_code, turn)

@app.route("/getboard")
def getboard():
    game_code = request.form.get('code')
    return get_board(game_code)

@app.route("/setboard", methods=['POST'])
def setboard():
    game_code = request.form.get('code')
    auth_token = request.form.get('auth_token')
    board = request.form.get('board')
    if (auth_token and check_creds(auth_token, game_code)):
        set_board(game_code, board)
    elif get_turn(game_code) == "blue":
        set_board(game_code, board)



if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0')