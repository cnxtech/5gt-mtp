# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- encoding: utf-8 -*-

from oslo_config import cfg

nova_client = cfg.OptGroup(name='nova_client',
                           title='Configuration Options for Nova')

NOVA_CLIENT_OPTS = [
    cfg.StrOpt('api_version',
               default='2',
               help='Version of Nova API to use in novaclient.'),
    cfg.StrOpt('endpoint_type',
               default='publicURL',
               help='Type of endpoint to use in novaclient.'
                    'Supported values: internalURL, publicURL, adminURL'
                    'The default is publicURL.')]


def register_opts(conf):
    conf.register_group(nova_client)
    conf.register_opts(NOVA_CLIENT_OPTS, group=nova_client)


def list_opts():
    return [('nova_client', NOVA_CLIENT_OPTS)]
