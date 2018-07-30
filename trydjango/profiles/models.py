from django.contrib.auth.models import User
from django.db import models
from django.db.models import CASCADE
from django.db.models.signals import post_save
from django.urls import reverse


class ProfileManager(models.Manager):

    def toggle_follow(self, request_user, toggled_username):
        is_following = False
        toggled_user_profile = self.get(user__username__iexact=toggled_username)
        if request_user in toggled_user_profile.followers.all():
            toggled_user_profile.followers.remove(request_user)
        else:
            toggled_user_profile.followers.add(request_user)
            is_following = True
        return toggled_user_profile, is_following


class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=CASCADE)
    followers = models.ManyToManyField(User, related_name='user_following', blank=True)
    activated = models.BooleanField(default=False)
    timestamp = models.DateTimeField(auto_now_add=True)
    updated = models.DateTimeField(auto_now=True)

    objects = ProfileManager()

    def __str__(self):
        return self.user.username

    def get_absolute_url(self):
        return reverse('profiles:detail', kwargs={'username': self.user.username})

def post_save_user_receiver(sender, instance, created, *args, **kwargs):
    if created:
        profile, is_created = Profile.objects.get_or_create(user=instance)


post_save.connect(post_save_user_receiver, sender=User)
