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


def get_images():
    r = requests.get(
        'http://127.0.0.1:8000/software_images')

    if r.status_code != 200:
        return False

    images = json.loads(r.text)

    return images


def create_images():
    payload = {
        'name': 'cdasilva-image',
        'provider': 'cdasilva',
        'visibility': 'private',
        'version': 'v1',
        'softwareImage': '/home/cdasilva/alpine-standard-3.8.0-x86_64.iso',
    }
    files = {
        'softwareImage': open(
            '/home/cdasilva/alpine-standard-3.8.0-x86_64.iso', 'rb')
    }
    r = requests.post(
        'http://127.0.0.1:8000/software_images', files=files, data=payload)
    image = json.loads(r.text)

    if r.status_code != 201:
        return False

    return image['id']


def delete_image(image_id):
    r = requests.delete('http://127.0.0.1:8000/software_images/' + image_id)

    deleted_resources = json.loads(r.text)

    return r.status_code == 200 and image_id in deleted_resources


def test_images():
    image_id = create_images()

    images = get_images()
    if not images:
        assert False

    delete_result = delete_image(image_id)
    if not delete_result:
        assert False

    assert True
