# Generated by Django 2.0.5 on 2018-06-26 11:38

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('restaurants', '0002_restaurant_location'),
    ]

    operations = [
        migrations.AddField(
            model_name='restaurant',
            name='category',
            field=models.CharField(blank=True, max_length=120, null=True),
        ),
    ]
