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

# # Compute

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


def create_compute_resources():
    network_id = create_network_resources()
    if not network_id:
        return False

    create_subnet(network_id)

    payload = {
        'computeName': 'cdasilva-compute',
        'computeFlavourId': '2',
        'vcImageId': 'b06cc64a-401a-4886-b91f-e917214bd207',
        'networkId': network_id
    }
    # 'computeFlavourId': '19fc95dc-9598-4b81-9d36-8d76bfee46d0',  # m1.small
    r = requests.post(
        'http://127.0.0.1:8000/compute_resources', json=payload)
    compute = json.loads(r.text)

    if r.status_code != 201:
        return False

    compute_id = compute['computeId']
    return compute_id


def get_compute_capacity():
    url = 'http://127.0.0.1:8000/compute_resources/capacities'
    r = requests.get(
        url + '?computeResourceTypeId=VirtualCpuResourceInformation')

    if r.status_code != 200:
        return False

    capacities = json.loads(r.text)

    return capacities


def get_compute_resources(compute_id):
    payload = {
        'computeResourceId': [compute_id]
    }
    r = requests.get(
        'http://127.0.0.1:8000/compute_resources', params=payload)

    if r.status_code != 200:
        return False

    resources = json.loads(r.text)

    compute_resource = [r for r in resources if r['computeId'] == compute_id]

    return len(compute_resource) == 1


def delete_compute_resources(compute_id):
    payload = {
        'computeId': [compute_id]
    }
    r = requests.delete(
        'http://127.0.0.1:8000/compute_resources', params=payload)

    deleted_resources = json.loads(r.text)

    return r.status_code == 200 and compute_id in deleted_resources


def test_compute_resources_management():
    compute_id = create_compute_resources()
    if not compute_id:
        assert False

    get_result = get_compute_resources(compute_id)
    if not get_result:
        assert False

    delete_result = delete_compute_resources(compute_id)
    if not delete_result:
        assert False

    assert True


def test_compute_capacity():
    compute_id = create_compute_resources()
    if not compute_id:
        assert False

    get_result = get_compute_capacity()
    if not get_result:
        assert False

    assert True
