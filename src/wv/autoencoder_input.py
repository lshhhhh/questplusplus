import re

import helpers


def inputs(file_path, max_seq):
    with open(file_path, 'r') as f:
        lines = f.readlines()
    new_lines = []
    for line in lines:
        new_lines.append([float(word) for word in line.split()])
    
    inputs = helpers.batch(new_lines, max_sequence_length=max_seq)
    return iter(inputs)


def next_batch(inputs, batch_size):
    # TODO but not important..
    return next(inputs)

"""
# Implementation with tensorflow TextLineDataset.
def inputs(file_path, shuffle=False, repeat_count=1, batch_size=16):
    dataset = tf.data.TextLineDataset(file_path)
    if shuffle:
        dataset = dataset.shuffle(buffer_size=256)
    dataset = dataset.repeat(repeat_count)
    dataset = dataset.padded_batch(batch_size, padded_shape=[108]) # TODO
    iterator = dataset.make_one_shot_iterator()
    batch_features = iterator.get_next()
    return batch_features
"""
