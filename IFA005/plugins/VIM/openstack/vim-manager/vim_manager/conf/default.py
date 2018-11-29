# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- encoding: utf-8 -*-

import os

from oslo_config import cfg

from vim_manager import log

GROUP_NAME = 'DEFAULT'
group = cfg.OptGroup(
    name=GROUP_NAME,
    title='Configuration Options for the service',
    help="All parameters can be overidden via environment variables",
)

OPTS = [
    cfg.StrOpt(
        'host',
        default=os.environ.get('HOST', '127.0.0.1'),
        help="Service host (default: HOST or '127.0.0.1')"),
    cfg.IntOpt(
        'port',
        default=os.environ.get('PORT', 8000),
        help="Service port (default: PORT or 8000)"),
    cfg.StrOpt(
        'app_secret',
        default=os.environ.get('APP_SECRET', 'not secret anymore'),
        help="App secret (default: 'APP_SECRET'"),
    cfg.BoolOpt(
        'debug',
        default=os.environ.get('DEBUG', False),
        help="Is in debug mode ? (default: DEBUG or false)"),
    cfg.StrOpt(
        'log_level',
        default=os.environ.get('LOG_LEVEL', 'warning'),
        choices=log.LOG_LEVELS.keys(),
        help="Service log level (default: LOG_LEVEL or 'warning')")
]


def register_opts(conf):
    conf.register_opts(OPTS, group=group)


def list_opts():
    return [(GROUP_NAME, OPTS)]
