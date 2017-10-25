#!/usr/bin/env bash

#locale
sudo echo "export LC_ALL=C" >> ~/.bashrc
export LC_ALL=C #in case it doesnt load from source
source ~/.bashrc

#install dependencies
sudo apt-get update
sudo apt-get -y upgrade
sudo apt-get install -y openjdk-8-jre-headless xvfb libxi6 libgconf-2-4
sudo apt-get install -y unzip

#install chrome
wget -N https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb -P ~/
sudo dpkg -i --force-depends ~/google-chrome-stable_current_amd64.deb
sudo apt-get -f install -y
sudo dpkg -i --force-depends ~/google-chrome-stable_current_amd64.deb

wget -N https://chromedriver.storage.googleapis.com/2.33/chromedriver_linux64.zip -P ~/
unzip ~/chromedriver_linux64.zip -d ~/
rm ~/chromedriver_linux64.zip

#install pip3
sudo apt-get install -y python3-pip
sudo apt-get install build-essential libssl-dev libffi-dev python3-dev
sudo apt-get install -y python3-venv
mkdir -p ~/.virtualenvs
cd ~/.virtualenvs
pyvenv my_env
source ~/.virtualenvs/my_env/bin/activate
pip install beautifulsoup4
pip install selenium

#for virtual display
Xvfb -ac :99 -screen 0 1280x1024x16 &
export DISPLAY=:99
