# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- coding: utf-8 -*-

from vim_manager.api.network.interfaces import capacity
from vim_manager.api.network.interfaces import information_management
from vim_manager.api.network.interfaces import management


def register_blueprints(app):
    """Register Flask blueprints."""
    app.register_blueprint(capacity.blueprint)
    app.register_blueprint(information_management.blueprint)
    app.register_blueprint(management.blueprint)
