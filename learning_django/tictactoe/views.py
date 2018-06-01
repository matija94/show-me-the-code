from django.http import HttpResponse


def welcome(request):
    return HttpResponse("Hello world! This is tictactoe module")