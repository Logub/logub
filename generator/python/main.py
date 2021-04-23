import logging
import time
import string
import random

logging.basicConfig(
    format='%(asctime)s %(levelname)-8s %(message)s',
    level=logging.INFO,
    datefmt='%Y-%m-%d %H:%M:%S')

while True:
    logging.info(''.join(random.choice(string.ascii_lowercase) for i in range(50)))
    time.sleep(5)