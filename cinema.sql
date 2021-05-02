-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3334
-- Время создания: Апр 29 2021 г., 00:06
-- Версия сервера: 5.6.43
-- Версия PHP: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `cinema`
--

-- --------------------------------------------------------

--
-- Структура таблицы `movies`
--

CREATE TABLE `movies` (
  `id` int(10) NOT NULL,
  `title` varchar(100) NOT NULL,
  `dateTime` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `movies`
--

INSERT INTO `movies` (`id`, `title`, `dateTime`) VALUES
(13, 'Отец', '2021-05-08 17:00:00'),
(17, 'Поколение вояджер', '2021-05-11 16:30:00'),
(18, 'Великий', '2021-05-12 17:00:00'),
(19, 'Любовь', '2021-05-20 09:15:00'),
(25, 'Звездные войны', '2021-05-06 15:00:00');

-- --------------------------------------------------------

--
-- Структура таблицы `tickets`
--

CREATE TABLE `tickets` (
  `id` int(10) NOT NULL,
  `buyer` varchar(100) DEFAULT NULL,
  `movie` varchar(100) NOT NULL,
  `movieId` int(10) NOT NULL,
  `place` int(2) NOT NULL,
  `price` int(10) NOT NULL,
  `isSold` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `tickets`
--

INSERT INTO `tickets` (`id`, `buyer`, `movie`, `movieId`, `place`, `price`, `isSold`) VALUES
(21, 'user44', 'Отец', 13, 1, 6, 1),
(22, NULL, 'Отец', 13, 2, 6, 0),
(23, NULL, 'Отец', 13, 3, 6, 0),
(24, NULL, 'Отец', 13, 4, 6, 0),
(25, NULL, 'Отец', 13, 5, 6, 0),
(26, NULL, 'Отец', 13, 6, 6, 0),
(27, NULL, 'Отец', 13, 7, 6, 0),
(28, NULL, 'Отец', 13, 8, 6, 0),
(49, NULL, 'Поколение вояджер', 17, 1, 8, 0),
(50, NULL, 'Поколение вояджер', 17, 2, 8, 0),
(51, NULL, 'Поколение вояджер', 17, 3, 8, 0),
(52, NULL, 'Поколение вояджер', 17, 4, 8, 0),
(53, NULL, 'Поколение вояджер', 17, 5, 8, 0),
(54, NULL, 'Поколение вояджер', 17, 6, 8, 0),
(55, NULL, 'Поколение вояджер', 17, 7, 8, 0),
(56, NULL, 'Поколение вояджер', 17, 8, 8, 0),
(57, NULL, 'Великий', 18, 1, 50, 0),
(58, NULL, 'Великий', 18, 2, 50, 0),
(59, NULL, 'Любовь и монстры', 19, 1, 8, 0),
(60, NULL, 'Любовь и монстры', 19, 2, 8, 0),
(61, 'user123', 'Любовь и монстры', 19, 3, 8, 1),
(62, 'user', 'Любовь и монстры', 19, 4, 8, 1),
(63, 'user44', 'Любовь и монстры', 19, 5, 8, 1),
(99, 'user123', 'Звездные войны', 25, 1, 5, 1),
(100, 'user123', 'Звездные войны', 25, 2, 5, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `login` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `access` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `login`, `password`, `access`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'ADMIN'),
(2, 'manager', '1d0258c2440a8d19e716292b231e3190', 'MANAGER'),
(4, 'Deles', '81dc9bdb52d04dc20036dbd8313ed055', 'USER'),
(9, 'user', 'ee11cbb19052e40b07aac0ca060c23ee', 'USER'),
(11, 'user55', '202cb962ac59075b964b07152d234b70', 'USER'),
(19, 'newuser4', 'b6d767d2f8ed5d21a44b0e5886680cb9', 'USER'),
(21, 'user44', 'f7177163c833dff4b38fc8d2872f1ec6', 'USER'),
(24, 'user123', '202cb962ac59075b964b07152d234b70', 'USER'),
(25, 'user22', 'b6d767d2f8ed5d21a44b0e5886680cb9', 'USER'),
(26, 'user99', 'ac627ab1ccbdb62ec96e702f07f6425b', 'USER'),
(27, 'user000', 'c6f057b86584942e415435ffb1fa93d4', 'USER'),
(29, '123456', 'e10adc3949ba59abbe56e057f20f883e', 'USER');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `movies`
--
ALTER TABLE `movies`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT для таблицы `tickets`
--
ALTER TABLE `tickets`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT для таблицы `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
