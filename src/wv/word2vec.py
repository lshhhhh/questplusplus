import os
import re
from gensim.models.word2vec import Word2Vec


def get_sentences(file_path):
    with open(file_path) as f:
        lines = f.readlines()
    sentences = [re.sub('[,.?!()/:;\'\"\[\]-]', ' ', line.lower()).split() 
                                                     for line in lines]
    return sentences


def preprocess(data_path, lan):
    model = Word2Vec.load('model/word2vec.{}'.format(lan))
    sentences = get_sentences('{}.{}'.format(data_path, lan))
    with open('{}.vec.{}'.format(data_path, lan), 'w') as f:
        for sentence in sentences:
            f.write(' '.join((' '.join(str(v) for v in model.wv[word])
                                              for word in sentence 
                                              if word in model.wv.vocab)) + '\n')
    print('Complete to preprocess {}.{} file'.format(data_path, lan))


if __name__ == '__main__':
    #TODO
    lan = 'en'
    """
    # Construct word2vec model.
    
    min_count = 10
    vector_size = 30
    sentences = get_sentences('data/word2vec/europarl-v7.es-en.{}'.format(lan))

    model = Word2Vec(sentences, min_count=min_count, size=vector_size)
    model.init_sims(replace=True)
    print(len(model.wv.vocab))
    model.save('model/word2vec.{}'.format(lan))
    """
    
    """
    # Preprocess.
    
    for data_path in ['data/train', 'data/test']:
        for lan in ['es', 'en']:
            preprocess(data_path, lan)
    """
    
    """
    f = open('data/test.en', 'r')
    lines = f.readlines()
    f.close()
    word_list = []
    for line in lines:
        line = re.sub('[^a-zA-Z]', ' ', line.lower())
        words = line.split()
        word_list += words
    wordset_test = set(word_list)

    print(len(wordset_train))
    print(len(wordset_test))
    print(len(wordset_test - wordset_train))
    print(wordset_test - wordset_train)
    """
