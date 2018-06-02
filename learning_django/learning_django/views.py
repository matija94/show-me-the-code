from importlib import import_module

from django.shortcuts import render
from django.urls import URLResolver


def home(request):
    apps = __get_apps()
    return render(request, "learning_django/learning_django.html", {"apps":apps})


def __get_apps():
    apps = {}
    urls_module = import_module("learning_django.urls")
    urlpatterns  = getattr(urls_module, "urlpatterns", None)
    for urlpattern in urlpatterns:
        if isinstance(urlpattern, URLResolver):
            app_name = urlpattern.app_name
            apps[app_name] = urlpattern.pattern._route
    return apps
