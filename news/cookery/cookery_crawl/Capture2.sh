gst-launch-1.0 ximagesrc use-damage=0 ! video/x-raw,framerate=30/1 ! videoconvert ! x264enc !  h264parse ! avimux ! filesink location=video.avi
