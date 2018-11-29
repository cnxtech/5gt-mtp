# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- coding: utf-8 -*-

import pytest


@pytest.mark.usefixtures('testapp')
def test_healthcheck(testapp):
    response = testapp.get('/healthz')
    assert response.status_code == 200
