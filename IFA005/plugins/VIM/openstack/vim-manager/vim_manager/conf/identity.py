# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- encoding: utf-8 -*-

import os

from oslo_config import cfg

GROUP_NAME = 'identity'
group = cfg.OptGroup(
    name=GROUP_NAME,
    title="Configuration Options for identity",
    help="All parameters can be overidden via environment variables",
)

OPTS = [
    cfg.StrOpt('driver',
               default=os.environ.get('IDENTITY_DRIVER', 'keycloak'),
               help="Identity driver "
                    "(default: IDENTITY_DRIVER or 'keycloak')"),
    cfg.StrOpt('app_realm',
               default=os.environ.get('APP_REALM', 'falcon'),
               help="Application realm "
                    "(default: APP_REALM or 'falcon')"),
    cfg.StrOpt('client_id',
               default=os.environ.get('CLIENT_ID', 'cluster-service'),
               help="Service Client ID with full access to Keycloak"
                    "(default: CLIENT_ID or 'cluster-service')"),
    cfg.StrOpt('client_secret',
               default=os.environ.get(
                   'CLIENT_SECRET', '02305a63-42a3-4e22-9297-a98f7ea9a72e'),
               secret=True,
               help="Client secret key (default: CLIENT_SECRET or "
                    "02305a63-42a3-4e22-9297-a98f7ea9a72e)"),
]


def register_opts(conf):
    conf.register_opts(OPTS, group=group)


def list_opts():
    return [(GROUP_NAME, OPTS)]
