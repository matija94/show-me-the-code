from django.contrib.auth.models import User
from django.db import models

# Create your models here.


class Invitation(models.Model):
    from_user = models.ForeignKey(User, related_name="sent_invitations", on_delete=models.CASCADE)
    to_user = models.ForeignKey(User, related_name="received_invitations", on_delete=models.CASCADE)
    comment = models.CharField(max_length=100)
    timestamp = models.DateTimeField(auto_now_add=True)