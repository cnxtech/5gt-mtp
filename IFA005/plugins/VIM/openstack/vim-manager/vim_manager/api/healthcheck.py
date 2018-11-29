# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- coding: utf-8 -*-

import flask

blueprint = flask.Blueprint('healthcheck', __name__)


# API
@blueprint.route("/healthz")
def healthcheck_status():
    """Healthcheck

    ---
    responses:
        200:
            description: Healthcheck
    """
    return "OK", 200
