# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- encoding: utf-8 -*-

from keystoneauth1 import loading as ka_loading

CLIENTS_AUTH = 'clients_auth'


def register_opts(conf):
    ka_loading.register_session_conf_options(conf, CLIENTS_AUTH)
    ka_loading.register_auth_conf_options(conf, CLIENTS_AUTH)


def list_opts():
    return [('clients_auth', ka_loading.get_session_conf_options() +
            ka_loading.get_auth_common_conf_options())]
