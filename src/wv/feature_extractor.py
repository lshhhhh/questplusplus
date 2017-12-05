import tensorflow as tf
import autoencoder as ae


flags = tf.flags
flags.DEFINE_integer('feature_size', 16, 'Number of features')
flags.DEFINE_string('lan', None, 'Language(en or es)')
flags.DEFINE_string('train_or_test', None, 'File to extract features')
flags.DEFINE_boolean('trained', False, 'Whether the model is trained or not')
flags.DEFINE_integer('batch_size', 1, 'Batch size')
FLAGS = flags.FLAGS


if __name__ == '__main__':
    # TODO Tuning parameters. (Make config file.. Not important)
    feature_size = FLAGS.feature_size
    lan = FLAGS.lan
    train_or_test = FLAGS.train_or_test
    trained = FLAGS.trained
    batch_size = FLAGS.batch_size # only 1, not implemented yet.

    vector_size = 30
    num_epoch = 20
    learning_rate = 0.1
    
    if lan == 'en':
        max_wc = 108
    elif lan == 'es':
        max_wc = 115

    if train_or_test == 'train':
        num_sample = 1832
    elif train_or_test == 'test':
        num_sample = 422
    
    input_size = vector_size * max_wc
    total_batch = int(num_sample / batch_size)

    auto_encoder = ae.Autoencoder(
            batch_size=batch_size, input_size=input_size, 
            feature_size=feature_size, num_epoch=num_epoch, 
            total_batch=total_batch, learning_rate=learning_rate)

    if trained == False:
        auto_encoder.train('data/train.vec.{}'.format(lan))

    if train_or_test != None:
        infile = 'data/{}.vec.{}'.format(train_or_test, lan)
        outfile = 'output/{}.{}.{}.tsv'.format(train_or_test, lan, feature_size)
        auto_encoder.extract_features(infile=infile, outfile=outfile)

