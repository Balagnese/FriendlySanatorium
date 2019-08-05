import re


EMAIL_REGEX = re.compile(r"""(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])""", re.VERBOSE)

PHONE_REGEX = re.compile(r'''^(\+7|7|8)?[\s\-]?\(?[489][0-9]{2}\)?[\s\-]?[0-9]{3}[\s\-]?[0-9]{2}[\s\-]?[0-9]{2}$''', re.VERBOSE)

USERNAME_REGEX = re.compile(r"""[a-zA-Z0-9а-яёА-ЯЁ]{3,30}""", re.VERBOSE)

PASSWORD_REGEX = re.compile(r"""[a-zA-Z0-9\*\.%\!\$_,]{3,30}""", re.VERBOSE)

NAME_REGEX = re.compile(r"[a-zA-Zа-яёА-ЯЁ]{1, 30}", re.VERBOSE)
SURNAME_REGEX = re.compile(r"[a-zA-Zа-яёА-ЯЁ]{1, 30}", re.VERBOSE)
PATRONYMIC_REGEX = re.compile(r"[a-zA-Zа-яёА-ЯЁ]{1, 30}", re.VERBOSE)
