# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- encoding: utf-8 -*-

from apisdk import exception as apiexc
from werkzeug import exceptions as wexc


class ServiceException(apiexc.ApiException):
    pass


class ClientNotConfigured(ServiceException):
    """Service not found through discovery"""

    description = "'Your agent is not configured'"


class NotAuthorized(wexc.Unauthorized, ServiceException):
    """Not Authenticated"""


class PermissionDenied(wexc.Forbidden, ServiceException):
    """Does not have the right level of privileges"""

    description = ("Permission denied: %(role)s do not match "
                   "required roles (%(required)s)")


class BadRequest(wexc.BadRequest, ServiceException):
    """BadRequest"""


class Conflict(wexc.Conflict, ServiceException):
    """Conflict"""


class NotFound(wexc.NotFound, ServiceException):
    """Not Found"""


class ServiceNotFound(wexc.NotFound, ServiceException):
    """Service not found through discovery"""

    description = "No '%(type)s' service could be found"


class UnsupportedDriver(ServiceException):
    """Driver is not supported by the application"""

    description = "Driver '%(name)s' is not supported"


class LoadingError(ServiceException):
    description = "Error loading plugin '%(name)s'"


class ResourceNotFound(NotFound):
    description = "The %(name)s resource %(id)s could not be found"


class InvalidIdentity(BadRequest):
    description = "Expected a UUID but received: %(identity)s"


class ResourceAlreadyExists(Conflict):
    description = "A %(name)s with ID %(id)s already exists"


class AuthorizationFailure(Exception):
    description = "Authorization failure"
