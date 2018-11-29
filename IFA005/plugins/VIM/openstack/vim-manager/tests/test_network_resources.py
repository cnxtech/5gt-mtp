# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- coding: utf-8 -*

import json
import requests


# Resources

# # Network

def create_subnet(network_id):
    payload = {
        'networkResourceName': 'cdasilva-subnet-network',
        'reservationId': 'cdasilva-subnet-id',
        'networkResourceType': 'subnet',
        'typeSubnetData': {
            'networkId': network_id,
            'ipVersion': 'IPv4',
            'cidr': '192.168.0.0/24',
            'gatewayIp': '192.168.0.1',
            'isDhcpEnabled': 1
        }
    }
    r = requests.post(
        'http://127.0.0.1:8000/network_resources', json=payload)
    network = json.loads(r.text)

    if r.status_code != 201:
        return False

    subnet_id = network['subnetData']['resourceId']
    return subnet_id


def create_network_resources():
    payload = {
        'networkResourceName': 'cdasilva-network',
        'reservationId': 'cdasilva-network-id',
        'networkResourceType': 'network'
    }
    r = requests.post(
        'http://127.0.0.1:8000/network_resources', json=payload)
    network = json.loads(r.text)

    if r.status_code != 201:
        return False

    network_id = network['networkData']['networkResourceId']
    return network_id


def get_network_resources(network_id):
    payload = {
        'networkResourceId': [network_id]
    }
    r = requests.get(
        'http://127.0.0.1:8000/network_resources', params=payload)

    if r.status_code != 200:
        return False

    resources = json.loads(r.text)

    network_resource = [
        r for r in resources if r['networkResourceId'] == network_id]

    return len(network_resource) == 1


def delete_network_resources(network_id):
    payload = {
        'networkResourceId': [network_id]
    }
    r = requests.delete(
        'http://127.0.0.1:8000/network_resources', params=payload)

    deleted_resources = json.loads(r.text)

    return r.status_code == 200 and network_id in deleted_resources


def get_network_capacity(network_id):
    payload = {
        'zoneId': '',
        'networkResourceId': network_id,
        'resourceCriteria': '',
        'attributeSelector': '',
        'timePeriod': '',
    }
    r = requests.get(
        'http://127.0.0.1:8000/network_resources/capacities', params=payload)

    if r.status_code != 200:
        return False

    capacities = json.loads(r.text)

    return capacities


def test_network_resources_management():
    network_id = create_network_resources()
    if not network_id:
        assert False

    get_result = get_network_resources(network_id)
    if not get_result:
        assert False

    delete_result = delete_network_resources(network_id)
    if not delete_result:
        assert False

    assert True


def test_network_capacity():
    network_id = create_network_resources()
    if not network_id:
        assert False

    subnet_id = create_subnet(network_id)
    if not subnet_id:
        assert False

    get_result = get_network_capacity(network_id)
    if not get_result:
        assert False

    delete_result = delete_network_resources(network_id)
    if not delete_result:
        assert False

    assert True
