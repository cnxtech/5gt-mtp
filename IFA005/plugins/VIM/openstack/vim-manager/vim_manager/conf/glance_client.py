# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- encoding: utf-8 -*-

from oslo_config import cfg

glance_client = cfg.OptGroup(name='glance_client',
                             title='Configuration Options for Glance')

GLANCE_CLIENT_OPTS = [
    cfg.StrOpt('api_version',
               default='2',
               help='Version of Glance API to use in glanceclient.'),
    cfg.StrOpt('endpoint_type',
               default='publicURL',
               help='Type of endpoint to use in glanceclient.'
                    'Supported values: internalURL, publicURL, adminURL'
                    'The default is internalURL.')]


def register_opts(conf):
    conf.register_group(glance_client)
    conf.register_opts(GLANCE_CLIENT_OPTS, group=glance_client)


def list_opts():
    return [('glance_client', GLANCE_CLIENT_OPTS)]
