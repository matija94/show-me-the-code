from django.contrib.auth.models import User
from django.db import models

# Create your models here.
from django.db.models import CASCADE
from django.urls import reverse

from restaurants.models import Restaurant


class Item(models.Model):
    user = models.ForeignKey(User, on_delete=CASCADE)
    restaurant = models.ForeignKey(Restaurant, on_delete=CASCADE)
    name = models.CharField(max_length=120)
    contents = models.TextField(help_text='separate each item by comma')
    excludes = models.TextField(blank=True, null=True, help_text='separate each item by comma')
    public = models.BooleanField(default=True)
    timestamp = models.DateTimeField(auto_now_add=True)
    updated = models.DateTimeField(auto_now=True)

    class Meta:
        ordering = ['-updated', '-timestamp']

    def get_contents(self):
        return self.contents.split(',')

    def get_excludes(self):
        return self.excludes.split(',')

    def get_absolute_url(self):
        return reverse("menus:detail", kwargs={'pk': self.id})
