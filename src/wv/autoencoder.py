import tensorflow as tf
import numpy as np

import autoencoder_input
import helpers


class Autoencoder:
    def __init__(self, batch_size, input_size, 
            feature_size, num_epoch, total_batch, learning_rate):
        self.batch_size = batch_size
        self.input_size = input_size
        self.feature_size = feature_size
        self.num_epoch = num_epoch
        self.total_batch = total_batch
        
        """
        Layer: input -> encode -> decode -> output
        We need encode part to extract the feature from a sentence.
        """
        # Input and Output. [batch_size, input_size]
        self.X = tf.placeholder(tf.float32, [None, input_size])

        # Encoder layer.
        self.W_encode = tf.Variable(tf.random_normal([input_size, feature_size]))
        self.b_encode = tf.Variable(tf.random_normal([feature_size]))
        self.encoder = tf.nn.sigmoid(
                tf.add(tf.matmul(self.X, self.W_encode), self.b_encode))

        # Decoder layer.
        self.W_decode = tf.Variable(tf.random_normal([feature_size, input_size]))
        self.b_decode = tf.Variable(tf.random_normal([input_size]))
        self.decoder = tf.nn.sigmoid(
                tf.add(tf.matmul(self.encoder, self.W_decode), self.b_decode))

        self.loss = tf.reduce_mean(tf.pow(self.X - self.decoder, 2))
        self.train_op = tf.train.RMSPropOptimizer(learning_rate).minimize(self.loss)
        self.saver = tf.train.Saver()


    def train(self, infile):
        with tf.Session() as sess:
            tf.get_variable_scope().reuse_variables()
            sess.run(tf.global_variables_initializer())
            for epoch in range(self.num_epoch):
                total_loss = 0
                x_data = autoencoder_input.inputs(infile, self.input_size)
                for i in range(self.total_batch):
                    _, loss = sess.run([self.train_op, self.loss],
                                       feed_dict={self.X: [next(x_data)]})
                    total_loss += loss
                print('Epoch:', '%04d' % (epoch + 1),
                        'Avg. cost =', '{:.4f}'.format(total_loss / self.total_batch))
            self.saver.save(sess, './tmp/model.ckpt')
        print('Complete to train!')


    def extract_features(self, infile, outfile):
        f = open(outfile, 'w')
        with tf.Session() as sess:
            tf.get_variable_scope().reuse_variables()
            self.saver.restore(sess, './tmp/model.ckpt')
            x_data = autoencoder_input.inputs(infile, self.input_size)
            
            for i in range(self.total_batch):
                features = sess.run(self.encoder, 
                                    feed_dict={self.X: [next(x_data)]})
                for vector in features:
                    f.write('\t'.join([str(value) for value in vector])+'\n')
        f.close()
        print('Complete to extract features!')

