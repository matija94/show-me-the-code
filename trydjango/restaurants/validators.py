from django.core.exceptions import ValidationError

CATEGORIES = ['Mexican', 'Srpska', 'Rostilj', 'Kineska']


def validate_category(value):
    if value not in CATEGORIES:
        raise ValidationError("{value} not in {cats}".format(value=value, cats=str(CATEGORIES)))