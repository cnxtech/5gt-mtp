# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- encoding: utf-8 -*-

from oslo_config import cfg

from vim_manager.conf import clients_auth
from vim_manager.conf import default
from vim_manager.conf import discovery
from vim_manager.conf import glance_client
from vim_manager.conf import identity
from vim_manager.conf import neutron_client
from vim_manager.conf import nova_client

CONF = cfg.CONF
# ConfigOpts = cfg.ConfigOpts


def register_all_opts(conf):
    clients_auth.register_opts(conf)
    default.register_opts(conf)
    discovery.register_opts(conf)
    glance_client.register_opts(conf)
    identity.register_opts(conf)
    neutron_client.register_opts(conf)
    nova_client.register_opts(conf)


register_all_opts(CONF)
