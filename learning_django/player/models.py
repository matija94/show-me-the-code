from django.contrib.auth.models import User
from django.db import models

# Create your models here.


class Invitation(models.Model):
    from_user = models.ForeignKey(User, related_name="sent_invitations", on_delete=models.CASCADE)
    to_user = models.ForeignKey(User, related_name="received_invitations", verbose_name="User to invite",
                                help_text="Please select a user you want to invite", on_delete=models.CASCADE)
    comment = models.CharField(max_length=300, blank=True, help_text="Provide a message for the invited user",
                               verbose_name="Optional message")
    timestamp = models.DateTimeField(auto_now_add=True)