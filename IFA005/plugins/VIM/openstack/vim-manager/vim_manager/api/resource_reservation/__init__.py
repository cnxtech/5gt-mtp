# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- coding: utf-8 -*-

from vim_manager.api.resource_reservation import compute_view
from vim_manager.api.resource_reservation import network_view


def register_blueprints(app):
    """Register Flask blueprints."""
    app.register_blueprint(compute_view.blueprint)
    app.register_blueprint(network_view.blueprint)
