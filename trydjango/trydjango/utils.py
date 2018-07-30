import random
import string

from django.utils.text import slugify


DONT_USE = ['create']

def random_string_generator(size=10, chars=string.ascii_lowercase + string.digits):
    return ''.join(random.choice(chars) for _ in range(size))


def unique_slug_generator(instance, new_slug=None):
    if new_slug is not None:
        slug = new_slug
    else:
        slug = slugify(instance.title)

    if slug in DONT_USE:
        new_slug = "{slug}-{randstr}".format(slug=slug, randstr=random_string_generator(size=4))
        return unique_slug_generator(instance, new_slug)

    klass = instance.__class__
    qs = klass.objects.filter(slug=slug).exists()
    if qs:
        new_slug = "{slug}-{randstr}".format(slug=slug, randstr=random_string_generator(size=4))
        return unique_slug_generator(instance, new_slug)
    return slug

