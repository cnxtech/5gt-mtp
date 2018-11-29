# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- coding: utf-8 -*-

import daiquiri
import logging


CRITICAL = logging.CRITICAL
FATAL = logging.FATAL
ERROR = logging.ERROR
WARNING = logging.WARNING
WARN = logging.WARN
INFO = logging.INFO
DEBUG = logging.DEBUG
NOTSET = logging.NOTSET

LOG_LEVELS = dict(
    critical=CRITICAL,
    fatal=FATAL,
    error=ERROR,
    warning=WARNING,
    warn=WARN,
    info=INFO,
    debug=DEBUG,
)


def setup(level=INFO):
    daiquiri.setup(level=level)


def get_logger(name):
    return daiquiri.getLogger(name)


setup()
