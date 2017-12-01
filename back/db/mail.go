package db

import "github.com/pkg/errors"

// AddMail adds a mail in redis
func AddMail(mail string) error {
	if err := sadd("mail", mail); err != nil {
		return errors.Wrap(err, "Could not add mail in redis")
	}

	return nil
}

// HasMail checks if a mail is already in redis
func HasMail(mail string) (bool, error) {
	var b bool
	var err error
	if b, err = sismember("mail", mail); err != nil {
		return b, errors.Wrap(err, "Could not check if mail is in redis")
	}
	return b, nil
}
