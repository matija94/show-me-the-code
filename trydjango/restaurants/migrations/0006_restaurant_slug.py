# Generated by Django 2.0.5 on 2018-06-27 07:41

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('restaurants', '0005_auto_20180626_1209'),
    ]

    operations = [
        migrations.AddField(
            model_name='restaurant',
            name='slug',
            field=models.SlugField(blank=True, null=True),
        ),
    ]
