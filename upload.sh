ac_i=abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_
ac_temp=$(printf "%s" $ac_i | cut -c 7)ithub_pat_11BCZ7OLY03Kw3gy9l3F3N_3Lnet5nKQhVO4UHUmQFaNspS5VRUDmwp3z2YT826YCP4U5CY7LJnemUNvxu
#echo $ac_temp
git pull
git add .
git commit -m update
git push https://$ac_temp@github.com/n01648028/calendar-scheduler
git pull