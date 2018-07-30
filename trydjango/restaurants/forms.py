from django import forms

from restaurants.models import Restaurant


class RestaurantCreateFormEasy(forms.ModelForm):
    class Meta:
        model = Restaurant
        fields = ['name', 'location', 'category']

    def clean_name(self):
        name = self.cleaned_data.get('name')
        '''
        method to clean name field of the Restaurant model
        if fails to clean raise ValidationError
        :return: 
        '''
        return name