from django.contrib.auth.models import User
from django.db import models

# Create your models here.

class Task(models.Model):
    owner = models.ForeignKey(User, related_name="tasks", on_delete=models.CASCADE)
    completed = models.BooleanField(default=False)
    title = models.CharField(max_length=100)
    description = models.TextField()