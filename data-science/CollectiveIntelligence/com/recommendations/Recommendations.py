'''
Created on Mar 21, 2017

@author: matija

FUNCTIONS FOR COMPUTING SIMILARITY BETWEEN TWO DATASETS BASED ON IT'S KEYS
HERE WE USED PERSON AS KEY AND THEIRS VALUES ARE RATINGS FOR THE MOVIES

WE COMPUTED A SIMILARITIES IN THIS MODULE BASED ON TWO FUNCTIONS
1.EUCLIDEAN DISTANCE
2.PEARSON


FROM MY EXPERIANCE RUNNING THESE FUNCTIONS AGAINST SAME DATA SET 
1.EUCLIDEAN DISTANCE IS VERY HARSH ON THE DIFFERENT RATINGS AND DECREASES SIMILARITY BY BIG MARGIN
2.PEARSON IS NOT AS HARSH AS EUCLIDEAN AND LOOKS MORE NATURAL TO ME WHEN COMPUTING THE SIMILARITY RESULT

also from my experiance if the preferences are charted only in one region of the graph then pearson will behave very strange.
which means it looks good only if data is charted across the whole graph(or at least one preference take spot in every 'region')
'''
from math import sqrt
import numpy as np
import matplotlib.pyplot as plt



criticsCustom={
'Lisa Rose': {'Lady in the Water': 2.5, 'Snakes on a Plane': 3.5, 'Superman':2.5, 'Batman':2,
              'Just My Luck': 3.0, 'Superman Returns': 3.5, 'You, Me and Dupree': 2.5,
              'The Night Listener': 3.0},

'Gene Seymour': {'Lady in the Water': 3.0, 'Snakes on a Plane': 3.5, 'Superman':2.5, 'Batman':3,
                 'Just My Luck': 1.5, 'Superman Returns': 5.0, 'The Night Listener': 3.0,
                 'You, Me and Dupree': 3.5},

'Michael Phillips': {'Lady in the Water': 2.5, 'Snakes on a Plane': 3.0,'Superman':2.5, 'Batman':4,
                     'Superman Returns': 3.5, 'The Night Listener': 4.0},

'Claudia Puig': {'Snakes on a Plane': 3.5, 'Just My Luck': 3.0,'Superman':2.5, 'Batman':5,
                 'The Night Listener': 4.5, 'Superman Returns': 4.0,
 '    You, Me and Dupree': 2.5},

'Mick LaSalle': {'Lady in the Water': 3.0, 'Snakes on a Plane': 4.0,'Superman':2.5, 'Batman':5,
                 'Just My Luck': 2.0, 'Superman Returns': 3.0, 'The Night Listener': 3.0,
                 'You, Me and Dupree': 2.0},

'Jack Matthews': {'Lady in the Water': 3.0, 'Snakes on a Plane': 4.0,'Superman':2.5, 'Batman':4,
                  'The Night Listener': 3.0, 'Superman Returns': 5.0, 'You, Me and Dupree': 3.5},

'Toby':           {'Snakes on a Plane':3.0,'You, Me and Dupree':3.5,'Superman Returns':5.0, 'Lady in the Water':3.0,'Superman':2.5, 'Batman':3},

'Matija' : {'Batman':3}
}

# A dictionary of movie critics and their ratings of a small
# set of movies
critics={'Lisa Rose': {'Lady in the Water': 2.5, 'Snakes on a Plane': 3.5,
'Just My Luck': 3.0, 'Superman Returns': 3.5, 'You, Me and Dupree': 2.5,
'The Night Listener': 3.0},
'Gene Seymour': {'Lady in the Water': 3.0, 'Snakes on a Plane': 3.5,
'Just My Luck': 1.5, 'Superman Returns': 5.0, 'The Night Listener': 3.0,
'You, Me and Dupree': 3.5},
'Michael Phillips': {'Lady in the Water': 2.5, 'Snakes on a Plane': 3.0,
'Superman Returns': 3.5, 'The Night Listener': 4.0},
'Claudia Puig': {'Snakes on a Plane': 3.5, 'Just My Luck': 3.0,
'The Night Listener': 4.5, 'Superman Returns': 4.0,
'You, Me and Dupree': 2.5},
'Mick LaSalle': {'Lady in the Water': 3.0, 'Snakes on a Plane': 4.0,
'Just My Luck': 2.0, 'Superman Returns': 3.0, 'The Night Listener': 3.0,
'You, Me and Dupree': 2.0},
'Jack Matthews': {'Lady in the Water': 3.0, 'Snakes on a Plane': 4.0,
'The Night Listener': 3.0, 'Superman Returns': 5.0, 'You, Me and Dupree': 3.5},
'Toby': {'Snakes on a Plane':4.5,'You, Me and Dupree':1.0,'Superman Returns':4.0}}


# if two films were plot on the graph (x and y axis) and the people were charted in preference space( some coordinate in the graph(x,y))
# then this function will calculate the distance between the two
#the higher the value the similar preferences are (people share same opinions about films) 
def sim_euclidean_distance(prefs, person1, person2):
    #Get the list of shared items
    si = {}
    for item in prefs[person1]:
        if item in prefs[person2]:
            si[item]=1
    
    #if they have no ratings in common , return 0
    if len(si)==0: return 0
    
    #add up squares of all the differences
    sum_of_squares = sum( [pow(prefs[person1][item]-prefs[person2][item], 2) for item in prefs[person1] if item in prefs[person2]] ) 

    return 1/(1+sum_of_squares)

#person 1 critics are represented on x axis while person2 critics on y axis
#films are charted in preference space matching the value of the persons axis
#this function will plot the line which is exactly in the middle of the each of persons critics for every movie
# and will return a score which represents the 'similarity of persons preferences
def sim_pearson(prefs, person1, person2):
    #list of mutually rated items
    si={}
    
    for item in prefs[person1]:
        if item in prefs[person2]:
            si[item]=1

    #the number of the elements         
    n = len(si)
    #if no common critic subject
    if n==0: return 0
    
    #add up all the preferences
    sum1 = sum([prefs[person1][it] for it in si])
    sum2 = sum([prefs[person2][it] for it in si])

    #add up all squares of the preferences
    sum1SQ = sum( [pow(prefs[person1][it],2) for it in si] )
    sum2SQ = sum( [pow(prefs[person2][it],2) for it in si] )
    
    #sum up the products of the preferences(p1Pref*p2Pref)
    pSum = sum ( [prefs[person1][it] * prefs[person2][it] for it in si] ) 
    
    #compute pearson score
    num = pSum-(sum1*sum2/n)
    den = sqrt( (sum1SQ - pow(sum1,2)/n) * (sum2SQ - pow(sum2,2)/n) )
    if den==0: return 0
    
    return num/den

#compares every key(person) critics from data set against one entry(persons critics, in this case let it be mine)
#returns sorted list by best recommendations that should one follow(the argument person which was against whole set)
#so u can see which person have most similar tastes like you do and pick his advices for movies
def top_matches(prefs, person, n=5, similarity=sim_pearson ):
    scores=[(similarity(prefs,person,other), other) for other in prefs if other != person]
    
    
    #sort the list so the highest scores appear on top
    scores.sort(reverse=True)
    return scores[0:n] 


#gets recommendations for films that person(argument) didn't watch
# but the people who have watched the same film as person did watch those films
#reccommendations are working on weighted average prinicpal
#of every other user's rankings
def getRecommendations(prefs, person ,precomputedUserSims=None, similarity=sim_pearson):
    totals = {}
    simSums = {}
    if precomputedUserSims is None:
        for other in prefs:
            #don't compare me to myself
            if other==person: continue
            sim=similarity(prefs, person, other)
            
            # ignore scores of zero or lower
            if sim<=0: continue
            for item in prefs[other]:
                
                #only score movies that i haven't seen
                if item not in prefs[person] or prefs[person][item]==0:
                    #Similarity * Score
                    totals.setdefault(item,0)
                    totals[item]+=prefs[other][item]*sim
                    #Sum of similarities
                    simSums.setdefault(item,0)
                    simSums[item]+=sim
    else :
        for sim,user in precomputedUserSims:
            if person==user: continue
            for item in prefs[user]:
                
                if item not in prefs[person]:
                    totals.setdefault(item, 0)
                    totals[item] += prefs[user][item]*sim
                    
                    simSums.setdefault(item,0)
                    simSums[item]+=sim
    #Create normalized list
    #to minimize the advantage of the films that were reviewed by the more users 
    rankings = [(total/simSums[item], item) for item,total in totals.items()]
    
    rankings.sort(reverse=True)
    return rankings


def userSimilarities(prefs, person, n):
    return top_matches(prefs, person, n)


def tanimotoScore(a,b):
    c = [v for v in a if v in b]
    return (len(c)/(len(a)+len(b)-len(c)))

def transformPrefs(prefs):
    result={}
    for person in prefs:
        for item in prefs[person]:
            result.setdefault(item,{})
            #flip the result
            result[item][person] = prefs[person][item]
    
    return result

def calculateSimilarItems(prefs, n=10):
    #Create a dictionary of items showing which other items they are most similar to
    result = {}
    
    #invert the preference matrix to be item-centric
    itemPrefs = transformPrefs(prefs)
    c=0
    for item in itemPrefs:
        #Status updates for lage datasets
        c+=1
        if c%100==0: print "%d / %d" %(c,len(itemPrefs))
        #Find most similar items to this one
        scores = top_matches(itemPrefs, item, n, similarity=sim_euclidean_distance)
        result[item]=scores
    return result


def getRecommendedItems(prefs, itemMatch, user):
    userRatings = prefs[user]
    scores = {}
    totalSim = {}
    
    # Loop over items rated by this user
    for (item,rating) in userRatings.items():
        
        # Loop over items similar to this one
        for (similarity,item2) in itemMatch[item]:
            
            # Ignore if this user has already rated this item
            if item2 in userRatings: continue
            
            # Weighted sum of rating times similarity
            scores.setdefault(item2,0)
            scores[item2]+=similarity*rating
            
            # Sum of all the similarities
            totalSim.setdefault(item2,0)
            totalSim[item2]+=similarity
            
    # Divide each total score by total weighting to get an average
    rankings = [(score/totalSim[item], item) for item,score in scores.items()]
    
    # Return the rankings from highest to lowest
    rankings.sort()
    rankings.reverse()
    return rankings

def loadMoviesLens(path='/home/matija/Desktop/ml-latest-small/'):
    # Get movie titles
    movies = {}
    skipFirst=True
    for line in open(path + "/movies.csv"):
        if skipFirst : 
            skipFirst = not skipFirst
            continue
        (id,title)=line.split(',')[0:2]
        movies[id]=title
        
    # Load data
    prefs={}
    skipFirst=True
    for line in open(path + '/ratings.csv'):
        if skipFirst:
            skipFirst = not skipFirst
            continue
        (user,movieid,rating)=line.split(',')[0:3]
        prefs.setdefault(user,{})
        prefs[user][movies[movieid]]=float(rating)
    return prefs

print getRecommendations(critics, 'Toby')
precomputedUserSims = userSimilarities(critics, 'Toby', 5)
print getRecommendations(critics, 'Toby',precomputedUserSims)


