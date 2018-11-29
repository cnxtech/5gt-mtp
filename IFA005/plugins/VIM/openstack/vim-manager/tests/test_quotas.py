# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
# -*- coding: utf-8 -*

import json
import requests

# Quota

# # Compute


def create_quotas_compute_resources(project_id):
    payload = {
        'resourceGroupId': project_id,
        'virtualComputeQuota': {
            'numVCPUs': 2,
            'numVcInstances': 2,
            'virtualMemSize': 4096
        }
    }
    r = requests.post(
        'http://127.0.0.1:8000/quotas/compute_resources', json=payload)
    quotas = json.loads(r.text)
    return quotas


def get_quotas_compute_resources():
    r = requests.get('http://127.0.0.1:8000/quotas/compute_resources')

    if r.status_code != 200:
        return False

    quotas = json.loads(r.text)

    return quotas

# def get_network_resources(network_id):
#     payload = {
#         'networkResourceId': [network_id]
#     }
#     r = requests.get(
#             'http://127.0.0.1:8000/network_resources', params=payload)
#
#     if r.status_code != 200:
#         return False
#
#     resources = json.loads(r.text)
#
#     network_resource = [
#         r for r in resources if r['networkResourceId'] == network_id]
#
#     return len(network_resource) == 1


def delete_quotas_compute_resources(quota_id):
    url = 'http://127.0.0.1:8000/quotas/compute_resources?resourceGroupId='
    r = requests.delete(url + quota_id)

    deleted_quotas = json.loads(r.text)

    return r.status_code == 200 and quota_id in deleted_quotas


def test_quotas_network_resources():
    project_id = 'de30d94754e3482983d10121b0c76fd2'
    quota_id = create_quotas_compute_resources(project_id)
    if not quota_id:
        assert False

    get_result = get_quotas_compute_resources()
    if not get_result:
        assert False

    delete_result = delete_quotas_compute_resources(project_id)
    if not delete_result:
        assert False

    assert True


# # Network

def test_quotas_compute_resources_query():
    r = requests.get('http://127.0.0.1:8000/quotas/compute_resources')
    print(r.text)
    assert r.status_code == 200


def test_quotas_network_resources_query():
    r = requests.get('http://127.0.0.1:8000/quotas/network_resources')
    print(r.text)
    assert r.status_code == 200
