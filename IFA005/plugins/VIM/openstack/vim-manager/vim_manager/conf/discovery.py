# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- encoding: utf-8 -*-

import os

from oslo_config import cfg

GROUP_NAME = "discovery"
group = cfg.OptGroup(
    name=GROUP_NAME,
    title="Configuration Options for the service discovery",
    help="All parameters can be overidden via environment variables",
)

OPTS = [
    cfg.StrOpt(
        "driver",
        default=os.environ.get("DISCOVERY_DRIVER", "static"),
        help="Driver name (default: DISCOVERY_DRIVER or 'static')"),
    cfg.DictOpt(
        "driver_config",
        default=cfg.types.Dict()(
            os.environ.get("DISCOVERY_DRIVER_CONFIG", dict())),
        help="discovery port (default: DISCOVERY_DRIVER_CONFIG or {})"),
]


def register_opts(conf):
    conf.register_opts(OPTS, group=group)


def list_opts():
    return [(GROUP_NAME, OPTS)]
