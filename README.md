# ChallengeKK

Приложение позволяет вводить на форму данные о событии (ФИО, подразделение, событие, тема, содержание) и отправлять его на сервер для сохранения.
Все поля являются обязательными для заполнения, если какие-то из них остаются пустыми, то отправить форму нельзя.
Пустое поле будет подсвечено красным, а на экран выведется сообщение о необходимости заполнения всех полей.
Если событие с такой же темой уже существует, сохранить его так же не получится. 

В приложении есть возможность выполнять поиск существующих событий по теме. 
Поле для поиска является автозаполняемым, автозаполнение работает при вводе 3 и более символов. 
При клике по одному из предложенных к автозаполнению вариантов на экран выведется (через DialogFragment)информация о существующем событии.

Роль сервера, куда необходимо отправлять формы для сохранения и по данным которого необходимо производить поиск, играет сервис "FakeServer", который асинхронно обрабатыват запросы.
Ввиду имитации сервера, данные о сохраненных формах (которые доступны для поиска), сохраняются только на протяжении работы приложения.
