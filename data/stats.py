from nba_api.stats.static import players
from nba_api.stats.endpoints import *
import random

players = players.get_players()
player1_id = random.choice(players)['id']
career = playercareerstats.PlayerCareerStats(player_id=player1_id)
career = career.get_dict()

print(career)