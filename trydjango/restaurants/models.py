from django.contrib.auth.models import User
from django.db import models
from django.db.models import CASCADE, Q
from django.db.models.signals import pre_save
from django.urls import reverse

from restaurants.validators import validate_category
from trydjango.utils import unique_slug_generator


class RestaurantQuerySet(models.query.QuerySet):
    def search(self, query):
        if query:
            return self.filter(
                Q(name__icontains=query) |
                Q(item__contents__icontains=query)
            )
        else:
            return self


class RestaurantManager(models.Manager):
    def get_queryset(self):
        return RestaurantQuerySet(self.model, self._db)

    def search(self, query):
        return self.get_queryset().search(query)


class Restaurant(models.Model):
    owner = models.ForeignKey(User, on_delete=CASCADE)
    name = models.CharField(max_length=120)
    location = models.CharField(max_length=120, null=True, blank=True)
    category = models.CharField(max_length=120, null=True, blank=True, validators=[validate_category])
    timestamp = models.DateTimeField(auto_now_add=True)
    updated = models.DateTimeField(auto_now=True)
    slug = models.SlugField(null=True, blank=True)

    objects = RestaurantManager()

    def __str__(self):
        return self.name

    @property
    def title(self):
        return self.name

    def get_absolute_url(self):
        return reverse("restaurants:detail", kwargs={"slug": self.slug})


def r_pre_save_receiver(sender, instance, *args, **kwargs):
    if not instance.slug:
        instance.slug = unique_slug_generator(instance)

#def r_post_save_receiver(sender, instance, created,   *args, **kwargs):
#    print('saved')
#    print(instance.timestamp)


pre_save.connect(r_pre_save_receiver, sender=Restaurant)
#post_save.connect(r_post_save_receiver, sender=Restaurant )