# `django-admin` 和 `manage.py`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-and-manage-py)

`django admin` 是 Django 用于管理任务的命令行实用程序。这份文件概述了它所能做的一切。

此外，`manage.py` 会在每个 Django 项目中自动创建。它做的事情和 `django-admin` 一样，但也设置了 [`DJANGO_SETTINGS_MODULE`](https://docs.djangoproject.com/zh-hans/5.1/topics/settings/#envvar-DJANGO_SETTINGS_MODULE) 环境变量，使其指向你的项目的 `settings.py` 文件。

如果你通过 `pip` 安装 Django，`django-admin` 脚本应该在你的系统路径中。如果它不在你的系统路径中，请确保你的虚拟环境已经被激活。

一般来说，当你在一个 Django 项目中工作时，使用 `manage.py` 比使用 `django-admin` 更容易。如果你需要在多个 Django 配置文件之间切换，可以使用 `django-admin` 与 [`DJANGO_SETTINGS_MODULE`](https://docs.djangoproject.com/zh-hans/5.1/topics/settings/#envvar-DJANGO_SETTINGS_MODULE) 或 [`--settings`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-settings) 命令行选项。

为了保持一致，本文中的命令行例子都使用了 `django-admin`，但任何例子都可以使用 `manage.py` 或 `python -m django`。



## 用法[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#usage)

```
$ django-admin <command> [options]
$ manage.py <command> [options]
$ python -m django <command> [options]
```

`command` 应是本文件所列的命令之一。`options` 是可选的，应该是 0 个或更多可用于指定命令的选项。



### 获得运行时帮助[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#getting-runtime-help)

- `django-admin help`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-help)

运行 `django-admin help` 来显示使用信息和每个应用程序提供的命令列表。

运行 `django-admin help --commands` 来显示所有可用命令的列表。

运行 `django-admin help <command>` 来显示命令的描述和可用选项的列表。



### 应用名称[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#app-names)

许多命令都需要一个“应用名称”的列表。一个“应用名称”是包含你模型的包的基名。例如，如果你的 [`INSTALLED_APPS`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-INSTALLED_APPS) 包含字符串 `'mysite.blog'`，则应用名称为 `blog`。



### 确定版本[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#determining-the-version)

- `django-admin version`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-version)

  

运行 `django-admin version` 来显示当前的 Django 版本。

输出遵循了 [**PEP 440**](https://peps.python.org/pep-0440/) 中描述的模式：

```
1.4.dev17026
1.4a1
1.4
```



### 显示 debug 输出[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#displaying-debug-output)

使用 [`--verbosity`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-verbosity)，如果支持的话，可以指定 `django-admin` 打印到控制台的通知和调试信息的数量。



## 可用命令[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#available-commands)



### `check`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#check)

- `django-admin check [app_label [app_label ...]]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-check)

  

使用 [系统检查框架](https://docs.djangoproject.com/zh-hans/5.1/ref/checks/) 来检查整个 Django 项目的常见问题。

默认情况下，将检查所有应用程序。你可以通过提供应用程序标签的列表作为参数来检查一部分应用程序：

```
django-admin check auth admin myapp
```

- `--tag`` TAGS``, ``-t`` TAGS`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-check-tag)

  

系统检查框架执行许多不同类型的检查，这些检查被 [分类为标签](https://docs.djangoproject.com/zh-hans/5.1/ref/checks/#system-check-builtin-tags)。你可以使用这些标签将执行的检查限制为特定类别中的检查。例如，要只执行模型和兼容性检查，请运行：

```
django-admin check --tag models --tag compatibility
```

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-check-database)

  

指定要运行需要数据库访问的检查的数据库：

```
django-admin check --database default --database other
```

默认情况下，这些检查不会被运行。

- `--list-tags```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-check-list-tags)

  

列出所有可用的标签。

- `--deploy```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-check-deploy)

  

激活一些仅在部署环境中相关的附加检查。

你可以在本地开发环境中使用这个选项，但由于你的本地开发设置模块可能不包含许多生产设置，所以你可能希望将 `check` 命令指向不同的设置模块，可以通过设置 [`DJANGO_SETTINGS_MODULE`](https://docs.djangoproject.com/zh-hans/5.1/topics/settings/#envvar-DJANGO_SETTINGS_MODULE) 环境变量或通过传递 `--settings` 选项来实现：

```
django-admin check --deploy --settings=production_settings
```

或者你可以直接在生产或暂存部署上运行它，以验证是否使用了正确的配置（省略 `--settings`）。你甚至可以把它作为集成测试套件的一部分。

- `--fail-level`` {CRITICAL,ERROR,WARNING,INFO,DEBUG}`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-check-fail-level)

  

指定导致命令以非零状态退出的消息级别。默认值是 `ERROR`。



### `compilemessages`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#compilemessages)

- `django-admin compilemessages`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-compilemessages)

  

将 [`makemessages`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-makemessages) 创建的 `.po` 文件编译成 `.mo` 文件，以用于内置的 gettext 支持。参见 [国际化和本地化](https://docs.djangoproject.com/zh-hans/5.1/topics/i18n/)。

- `--locale`` LOCALE``, ``-l`` LOCALE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-compilemessages-locale)

  

指定要处理的 locale。如果没有提供，则会处理所有的 locale。

- `--exclude`` EXCLUDE``, ``-x`` EXCLUDE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-compilemessages-exclude)

  

指定要从处理中排除的 locale。如果没有提供，则不排除任何 locale。

- `--use-fuzzy````, ``-f```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-compilemessages-use-fuzzy)

  

包括 [fuzzy 翻译](https://www.gnu.org/software/gettext/manual/html_node/Fuzzy-Entries.html) 到编译文件。

使用实例：

```
django-admin compilemessages --locale=pt_BR
django-admin compilemessages --locale=pt_BR --locale=fr -f
django-admin compilemessages -l pt_BR
django-admin compilemessages -l pt_BR -l fr --use-fuzzy
django-admin compilemessages --exclude=pt_BR
django-admin compilemessages --exclude=pt_BR --exclude=fr
django-admin compilemessages -x pt_BR
django-admin compilemessages -x pt_BR -x fr
```

- `--ignore`` PATTERN``, ``-i`` PATTERN`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-compilemessages-ignore)

  

忽略与给定 [`glob`](https://docs.python.org/3/library/glob.html#module-glob) 风格的模式相匹配的目录。使用多次可以忽略更多的目录。

使用实例：

```
django-admin compilemessages --ignore=cache --ignore=outdated/*/locale
```



### `createcachetable`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#createcachetable)

- `django-admin createcachetable`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-createcachetable)

  

使用你的配置文件中的信息创建用于数据库缓存后台的缓存表。更多信息请参见 [Django 缓存框架](https://docs.djangoproject.com/zh-hans/5.1/topics/cache/)。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-createcachetable-database)

  

指定创建缓存表的数据库。默认值为 `default`。

- `--dry-run```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-createcachetable-dry-run)

  

打印无需实际运行的 SQL，所以你可以自定义它或使用迁移框架。



### `dbshell`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#dbshell)

- `django-admin dbshell`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-dbshell)

  

运行你的 [`ENGINE`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-DATABASE-ENGINE) 配置中指定的数据库引擎的命令行客户端，连接参数在你的 [`USER`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-USER)、 [`PASSWORD`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-PASSWORD) 等配置中指定。

- 对于 PostgreSQL 来说，这将运行 `psql` 命令行客户端。
- 对于 MySQL 来说，这将运行 `mysql` 命令行客户端。
- 对于 SQLite 来说，这将运行 `sqlite3` 命令行客户端。
- 对于 Oracle 来说，这将运行 `sqlplus` 命令行客户端。

这个命令假设程序在你的 `PATH` 上，这样调用程序名（`psql`、`mysql`、`sqlite3`、`sqlplus`）就能在正确的地方找到程序。没有办法手动指定程序的位置。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-dbshell-database)

  

指定打开命令行的数据库。默认为 `default`。

- `--`` ARGUMENTS`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-dbshell-0)

  

`--` 分界线后的任何参数都会被传递给底层的命令行客户端。例如，对于 PostgreSQL，你可以使用 `psql` 命令的 `-c` 标志直接执行一个原始 SQL 查询：

```
$ django-admin dbshell -- -c 'select current_user'
 current_user
--------------
 postgres
(1 row)
```

在 MySQL／MariaDB 上，你可以用 `mysql` 命令的 `-e` 标志来实现：

```
$ django-admin dbshell -- -e "select user()"
+----------------------+
| user()               |
+----------------------+
| djangonaut@localhost |
+----------------------+
```

备注

要注意的是，在数据库配置的 [`OPTIONS`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-OPTIONS) 部分的 [`DATABASES`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-DATABASES) 中，并不是所有的选项都会传递给命令行客户端，例如 `'isolation_level'`。



### `diffsettings`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#diffsettings)

- `django-admin diffsettings`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-diffsettings)

  

显示当前设置文件与 Django 默认配置（或由 [`--default`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-diffsettings-default) 指定的其他配置文件）之间的差异。

默认配置中没有出现的配置，后面都是 `"##"`。例如，默认配置没有定义 [`ROOT_URLCONF`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-ROOT_URLCONF)，所以 [`ROOT_URLCONF`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-ROOT_URLCONF) 在 `diffsettings` 的输出中，后面跟了 `"##"`。

- `--all```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-diffsettings-all)

  

显示所有的配置，即使它们有 Django 的默认值。这些配置的前缀是 `"##"`。

- `--default`` MODULE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-diffsettings-default)

  

要与当前配置进行比较的配置模块。留空以便与 Django 的默认配置进行比较。

- `--output`` {hash,unified}`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-diffsettings-output)

  

指定输出格式。可用值是 `hash` 和 `unified`。`hash` 是默认模式，显示上述的输出。`unified` 显示的输出类似于 `diff -u`。缺省配置的前面是减号，后面是改变后的配置，前面是加号。



### `dumpdata`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#dumpdata)

- `django-admin dumpdata [app_label[.ModelName] [app_label[.ModelName] ...]]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-dumpdata)

  

将数据库中与指定应用程序相关联的所有数据输出到标准输出。

如果没有提供应用程序名称，所有安装的应用程序将被转储。

`dumpdata` 的输出可以作为 [`loaddata`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-loaddata) 的输入。

当 `dumpdata` 的结果被保存为一个文件时，它可以作为 [fixture](https://docs.djangoproject.com/zh-hans/5.1/topics/db/fixtures/#fixtures-explanation) 用于 [tests](https://docs.djangoproject.com/zh-hans/5.1/topics/testing/tools/#topics-testing-fixtures) 或作为 [initial data](https://docs.djangoproject.com/zh-hans/5.1/howto/initial-data/#initial-data-via-fixtures)。

注意 `dumpdata` 使用模型上的默认管理器来选择要转储的记录。如果你使用 [自定义管理器](https://docs.djangoproject.com/zh-hans/5.1/topics/db/managers/#custom-managers) 作为默认管理器，并且它过滤了一些可用的记录，那么并非所有的对象都会被转储。

- `--all````, ``-a```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-dumpdata-all)

  

使用 Django 的基础管理器，转储那些可能被自定义管理器过滤或修改的记录。

- `--format`` FORMAT`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-dumpdata-format)

  

指定输出的序列化格式。默认为 JSON。支持的格式在 [序列化格式](https://docs.djangoproject.com/zh-hans/5.1/topics/serialization/#serialization-formats) 中列出。

- `--indent`` INDENT`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-dumpdata-indent)

  

指定输出中使用的缩进空格数。默认值为 `None`，在单行上显示所有数据。

- `--exclude`` EXCLUDE``, ``-e`` EXCLUDE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-dumpdata-exclude)

  

防止特定的应用程序或模型（以 `app_label.ModelName` 的形式指定）被转储。如果你指定一个模型名称，那么只有该模型将被排除，而不是整个应用程序。你也可以混合应用程序名称和模型名称。

如果要排除多个应用程序，请多次传递 `--exclude` 参数：

```
django-admin dumpdata --exclude=auth --exclude=contenttypes
```

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-dumpdata-database)

  

指定转储数据的数据库。默认值为 `default`。

- `--natural-foreign```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-dumpdata-natural-foreign)

  

使用 `natural_key()` 模型方法将任何外键和多对多关系序列化到定义该方法的类型的对象。 请参阅 [自然键](https://docs.djangoproject.com/zh-hans/5.1/topics/serialization/#topics-serialization-natural-keys) 文档，了解更多关于这个和下一个选项的细节。

- `--natural-primary```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-dumpdata-natural-primary)

  

省略该对象序列化数据中的主键，因为它可以在反序列化过程中计算。

- `--pks`` PRIMARY_KEYS`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-dumpdata-pks)

  

只输出由逗号分隔的主键列表指定的对象。这仅在转储一个模型时可用。默认情况下，输出模型的所有记录。

- `--output`` OUTPUT``, ``-o`` OUTPUT`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-dumpdata-output)

  

指定要将序列化数据写入的文件。默认情况下，数据将被写入标准输出。

当设置了这个选项，并且 `--verbosity` 大于 0（默认值）时，终端会显示一个进度条。



#### 固定数据压缩[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#fixtures-compression)

输出文件可以使用 `bz2`、`gz`、`lzma` 或 `xz` 格式之一进行压缩，只需在文件名末尾添加相应的扩展名即可。例如，要将数据输出为压缩的 JSON 文件：

```
django-admin dumpdata -o mydata.json.gz
```



### `flush`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#flush)

- `django-admin flush`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-flush)

  

从数据库中删除所有数据，并重新执行任何同步后处理程序。已应用迁移的表不会被清除。

如果你更愿意从一个空的数据库开始，并重新运行所有迁移，你应该删除并重新创建数据库，然后运行 [`migrate`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-migrate)。

- `--noinput````, ``--no-input```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-flush-noinput)

  

禁止所有的用户提示。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-flush-database)

  

指定要刷新的数据库。默认为 `default`。



### `inspectdb`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#inspectdb)

- `django-admin inspectdb [table [table ...]]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-inspectdb)

  

对 [`NAME`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-NAME) 配置指向的数据库中的数据库表进行检查，并将一个 Django 模型模块（`models.py` 文件）输出到标准输出。

你可以通过传递表或视图的名称作为参数来选择要检查的表或视图。如果没有提供参数，只有在使用 [`--include-views`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-inspectdb-include-views) 选项时，才会为视图创建模型。如果使用了 [`--include-partitions`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-inspectdb-include-partitions) 选项，则会在 PostgreSQL 上为分区表创建模型。

如果你有一个遗留的数据库，并且你想使用 Django，那么就使用这个脚本。脚本将检查数据库，并为其中的每个表创建一个模型。

正如你所期望的那样，创建的模型将为表中的每个字段提供一个属性。请注意，`inspectdb` 在其字段名输出中有一些特殊情况：

- 如果 `inspectdb` 不能将列的类型映射到模型字段类型，它将使用 `TextField`，并在生成的模型中的字段旁边插入 Python 注释 `'This field type is a guess.'`。识别的字段可能取决于 [`INSTALLED_APPS`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-INSTALLED_APPS) 中列出的应用程序。例如，[`django.contrib.postgres`](https://docs.djangoproject.com/zh-hans/5.1/ref/contrib/postgres/#module-django.contrib.postgres) 增加了对几个 PostgreSQL 特定字段类型的识别。
- 如果数据库列名是 Python 的保留字（如 `'pass'`、`'class'` 或 `'for'`），`inspectdb` 将在属性名后面附加 `'_field'`。例如，如果一个表有一列 `'for'`，生成的模型将有一个字段 `'for_field'`，`db_column` 属性设置为 `'for'`。`inspectdb` 将在字段旁边插入 Python 注释 `'Field renamed because it was a Python reserved word.'`。

这个功能是作为一个快捷方式，而不是作为明确的模型生成。在你运行它之后，你将希望自己查看生成的模型以进行自定义。特别是，你需要重新安排模型的顺序，使引用其他模型的模型正确排序。

当在模型字段上指定了 [`default`](https://docs.djangoproject.com/zh-hans/5.1/ref/models/fields/#django.db.models.Field.default) 时，Django 不会创建数据库默认值。同样，数据库默认值也不会转化为模型字段默认值，也不会被 `inspectdb` 以任何方式检测到。

默认情况下，`inspectdb` 创建的是未管理的模型。也就是说，模型的 `Meta` 类中的 `managed = False` 告诉 Django 不要管理每个表的创建、修改和删除。如果你确实想让 Django 管理表的生命周期，你需要将 [`managed`](https://docs.djangoproject.com/zh-hans/5.1/ref/models/options/#django.db.models.Options.managed) 选项改为 `True` （或者将其删除，因为 `True` 是其默认值）。



#### 特定于数据库的注释[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#database-specific-notes)



##### Oracle[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#oracle)

- 如果使用 `--includ-views`，则会为物化视图创建模型。



##### PostgreSQL[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#postgresql)

- 为外部表创建模型。
- 如果使用 `--includ-views`，则会为物化视图创建模型。
- 如果使用 [`--include-partitions`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-inspectdb-include-partitions)，则为分区表创建模型。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-inspectdb-database)

  

指定要检查的数据库。默认为 `default`。

- `--include-partitions```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-inspectdb-include-partitions)

  

如果提供了这个选项，也会为分区创建模型。

只实现了对 PostgreSQL 的支持。

- `--include-views```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-inspectdb-include-views)

  

如果提供了这个选项，也会为数据库视图创建模型。



### `loaddata`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#loaddata)

- `django-admin loaddata fixture [fixture ...]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-loaddata)

  

搜索并加载指定名称的 [fixture](https://docs.djangoproject.com/zh-hans/5.1/topics/db/fixtures/#fixtures-explanation) 内容到数据库中。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-loaddata-database)

  

指定数据将被载入的数据库。默认值为 `default`。

- `--ignorenonexistent````, ``-i```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-loaddata-ignorenonexistent)

  

忽略自固定数据最初生成以来可能已经被删除的字段和模型。

- `--app`` APP_LABEL`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-loaddata-app)

  

指定一个单一的应用来寻找固定数据，而不是在所有的应用程序中寻找。

- `--format`` FORMAT`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-loaddata-format)

  

为 [从标准输入中读取](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#loading-fixtures-stdin) 的固定数据指定 [序列化格式](https://docs.djangoproject.com/zh-hans/5.1/topics/serialization/#serialization-formats) （例如，`json` 或 `xml`）。

- `--exclude`` EXCLUDE``, ``-e`` EXCLUDE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-loaddata-exclude)

  

排除从给定的应用程序和／或模型加载固定数据（以 `app_label` 或 `app_label.ModelName` 的形式）。多次使用该选项以排除一个以上的应用程序或模型。



#### 从 `stdin` 加载固定数据[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#loading-fixtures-from-stdin)

你可以使用短横线作为 fixture 名称来加载来自 `sys.stdin` 的输入。例如：

```
django-admin loaddata --format=json -
```

当从 `stdin` 读取时，需要使用 [`--format`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-loaddata-format) 选项来指定输入的 [序列化格式](https://docs.djangoproject.com/zh-hans/5.1/topics/serialization/#serialization-formats) （例如 `json` 或 `xml`）。

从 `stdin` 加载在标准输入和输出重定向时非常有用。例如：

```
django-admin dumpdata --format=json --database=test app_label.ModelName | django-admin loaddata --format=json --database=prod -
```

[`dumpdata`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-dumpdata) 命令可以用来生成 `loaddata` 的输入。

参见

有关 fixture 的更多详细信息，请参阅 [辅助工具](https://docs.djangoproject.com/zh-hans/5.1/topics/db/fixtures/#fixtures-explanation) 主题。



### `makemessages`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#makemessages)

- `django-admin makemessages`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-makemessages)

  

遍历当前目录下的整个源码树，并提取所有标记为翻译的字符串，并在 conf/locale（在 Django 树中）或 locale（对于项目和应用程序）目录下创建（或更新）一个消息文件。在对消息文件进行修改后，你需要使用 [`compilemessages`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-compilemessages) 来编译它们，以便使用内置的 gettext 支持。详情请看 [i18n 文档](https://docs.djangoproject.com/zh-hans/5.1/topics/i18n/translation/#how-to-create-language-files)。

该命令不需要设置配置。但是，当配置没有设置时，命令不能忽略 [`MEDIA_ROOT`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-MEDIA_ROOT) 和 [`STATIC_ROOT`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-STATIC_ROOT) 目录，也不能包含 [`LOCALE_PATHS`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-LOCALE_PATHS)。

- `--all````, ``-a```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-all)

  

更新所有可用语言的消息文件。

- `--extension`` EXTENSIONS``, ``-e`` EXTENSIONS`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-extension)

  

指定要检查的文件扩展名列表（默认：`html`、`txt`、`py` 或 `js` 如果 [`--domain`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-domain) 是 `djangojs`）。

使用实例：

```
django-admin makemessages --locale=de --extension xhtml
```

用逗号分隔多个扩展名或多次使用 `-e` 或 `--extension` 参数：

```
django-admin makemessages --locale=de --extension=html,txt --extension xml
```

- `--locale`` LOCALE``, ``-l`` LOCALE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-locale)

  

指定要处理的 locale。

- `--exclude`` EXCLUDE``, ``-x`` EXCLUDE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-exclude)

  

指定要从处理中排除的 locale。如果没有提供，则不排除任何 locale。

使用实例：

```
django-admin makemessages --locale=pt_BR
django-admin makemessages --locale=pt_BR --locale=fr
django-admin makemessages -l pt_BR
django-admin makemessages -l pt_BR -l fr
django-admin makemessages --exclude=pt_BR
django-admin makemessages --exclude=pt_BR --exclude=fr
django-admin makemessages -x pt_BR
django-admin makemessages -x pt_BR -x fr
```

- `--domain`` DOMAIN``, ``-d`` DOMAIN`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-domain)

  

指定消息文件的域。支持的选项有：

- `django` 适用于所有 `*.py`、`*.html` 和 `*.txt` 文件（默认）
- `djangojs` 适用于 `*.js` 文件

- `--symlinks````, ``-s```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-symlinks)

  

在寻找新的翻译字符串时，跟踪指向目录的符号链接。

使用实例：

```
django-admin makemessages --locale=de --symlinks
```

- `--ignore`` PATTERN``, ``-i`` PATTERN`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-ignore)

  

忽略与给定的 [`glob`](https://docs.python.org/3/library/glob.html#module-glob) 风格模式匹配的文件或目录。多次使用可以忽略更多的文件或目录。

这些模式默认使用：`'CVS'`、`'.*'`、`'*~'`、`'*.pyc'`。

使用实例：

```
django-admin makemessages --locale=en_US --ignore=apps/* --ignore=secret/*.html
```

- `--no-default-ignore```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-no-default-ignore)

  

禁用 `--ignore` 的默认值。

- `--no-wrap```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-no-wrap)

  

禁用将语言文件中的长消息行分成几行。

- `--no-location```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-no-location)

  

禁止在语言文件中写 '`#: filename:line`' 注释行。使用该选项会使技术熟练的译者更难理解每条消息的上下文。

- `--add-location`` [{full,file,never}]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-add-location)

  

控制语言文件中的 `#: filename:line` 注释行。如果该选项是：

- `full` （如果没有给出，则为默认值）：行中包括文件名和行号。
- `file` ：省略行号。
- `never` ：压制行数（与 [`--no-location`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-no-location) 相同）。

需要 `gettext` 0.19 或更新版本。

- `--no-obsolete```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-no-obsolete)

  

从 `.po` 文件中删除过时的消息字符串。

- `--keep-pot```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemessages-keep-pot)

  

防止删除在创建 `.po` 文件之前生成的临时 `.pot` 文件。这对调试可能妨碍最终语言文件创建的错误很有用。

参见

关于如何自定义 [`makemessages`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-makemessages) 传递给 `xgettext` 的关键字，请参见 [自定义 makemessages 命令](https://docs.djangoproject.com/zh-hans/5.1/topics/i18n/translation/#customizing-makemessages) 的说明。



### `makemigrations`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#makemigrations)

- `django-admin makemigrations [app_label [app_label ...]]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-makemigrations)

  

根据检测到的模型变化创建新的迁移。迁移，它们与应用程序的关系以及更多的内容在 [迁移文档](https://docs.djangoproject.com/zh-hans/5.1/topics/migrations/) 中深入介绍。

提供一个或多个应用名称作为参数，将把创建的迁移限制在指定的应用和任何所需的依赖关系上（例如，`ForeignKey` 的另一端的表）。

要将迁移添加到没有 `migrations` 目录的应用中，运行 `makemigrations`，并使用应用的 `app_label`。

- `--noinput````, ``--no-input```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemigrations-noinput)

  

压制所有用户提示。如果不能自动解决被抑制的提示，命令将以 error code 3 退出。

- `--empty```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemigrations-empty)

  

为指定的应用程序输出一个空的迁移，用于手动编辑。这是为高级用户准备的，除非你熟悉迁移格式、迁移操作和迁移之间的依赖关系，否则不应使用。

- `--dry-run```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemigrations-dry-run)

  

显示在不向磁盘写入任何迁移文件的情况下进行的迁移。使用这个选项和 `--verbosity 3` 也会显示将被写入的完整迁移文件。

- `--merge```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemigrations-merge)

  

可以解决迁移冲突。

- `--name`` NAME``, ``-n`` NAME`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemigrations-name)

  

允许对生成的迁移进行命名，而不是使用生成的名称。名称必须是有效的 Python [标识符](https://docs.python.org/3/reference/lexical_analysis.html#identifiers)。

- `--no-header```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemigrations-no-header)

  

生成没有 Django 版本和时间戳头的迁移文件。

- `--check```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemigrations-check)

  

在检测到模型更改而没有迁移时，使 `makemigrations` 以非零状态退出。意味着 `--dry-run`。

- `--scriptable```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemigrations-scriptable)

  

将日志输出和输入提示重定向到 `stderr`，仅将生成的迁移文件的路径写入 `stdout`。

- `--update```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-makemigrations-update)

  

将模型更改合并到最新的迁移中，并优化生成的操作。

更新后的迁移将具有生成的名称。为了保留先前的名称，请使用 `--name` 进行设置。



### `migrate`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#migrate)

- `django-admin migrate [app_label] [migration_name]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-migrate)

  

将数据库状态与当前的模型集和迁移同步。在 [迁移文档](https://docs.djangoproject.com/zh-hans/5.1/topics/migrations/) 中深入介绍了迁移，它们与应用的关系等。

该命令的行为根据提供的参数而改变：

- 没有参数：运行所有的应用程序的所有迁移。
- `<app_label>` ：指定的应用程序将运行其迁移，直至其最近的迁移。由于依赖关系，这可能涉及到运行其他应用的迁移。
- `<app_label> <migrationname>`: 将数据库模式调整到适用指定迁移的状态，但不适用同一应用程序中后来的迁移。如果你之前已经迁移过命名的迁移，这可能涉及到取消应用迁移。你可以使用迁移名称的前缀，例如 `0001`，只要它对给定的应用程序名称是唯一的。使用名称 `zero` 来回溯所有的迁移，即恢复一个应用的所有已应用迁移。

警告

当取消应用迁移时，所有依赖的迁移也将被取消应用，无论 `<app_label>`。你可以使用 `--plan` 来检查哪些迁移将被取消应用。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-migrate-database)

  

指定要迁移的数据库。默认值为 `default`。

- `--fake```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-migrate-fake)

  

标记目标的迁移（按照上面的规则）已应用，但没有实际运行 SQL 来改变你的数据库架构。

这是为高级用户准备的，如果他们要手动应用更改，可以直接操作当前的迁移状态；要注意的是，使用 `--fake` 会有将迁移状态表置于需要手动恢复才能使迁移正确运行的状态的风险。

- `--fake-initial```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-migrate-fake-initial)

  

允许 Django 在迁移中所有 [`CreateModel`](https://docs.djangoproject.com/zh-hans/5.1/ref/migration-operations/#django.db.migrations.operations.CreateModel) 操作创建的所有模型名称的数据库表已经存在的情况下，跳过应用的初始迁移。这个选项是为了在第一次针对一个在使用 migrations 之前就存在的数据库运行迁移时使用。但是，除了匹配表名之外，这个选项并不检查数据库架构是否匹配，因此只有当你确信你现有的架构与初始迁移中记录的内容相匹配时，才可以安全使用。

- `--plan```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-migrate-plan)

  

显示对给定的 `migrate` 命令将进行的迁移操作。

- `--run-syncdb```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-migrate-run-syncdb)

  

允许在没有迁移的情况下为应用创建表。虽然不推荐这样做，但在有数百个模型的大型项目中，迁移框架有时太慢。

- `--noinput````, ``--no-input```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-migrate-noinput)

  

压制所有用户提示。一个例子是询问有关删除陈旧内容类型的提示。

- `--check```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-migrate-check)

  

当检测到未应用的迁移时，使 `migrate` 以非零状态退出。

- `--prune```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-migrate-prune)

  

从 `django_migrations` 表中删除不存在的迁移。当迁移文件被压缩迁移替代后已被删除时，这是有用的。请参阅 [压缩迁移](https://docs.djangoproject.com/zh-hans/5.1/topics/migrations/#migration-squashing) 以获取更多详细信息。



### `optimizemigration`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#optimizemigration)

- `django-admin optimizemigration app_label migration_name`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-optimizemigration)

  

优化指定迁移的操作并覆盖现有文件。如果迁移包含必须手动复制的函数，该命令会创建一个新的迁移文件，后缀为 `_optimized`，旨在替代指定的迁移。

- `--check```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-optimizemigration-check)

  

当迁移可以优化时，使 `optimizemigration` 以非零状态退出。



### `runserver`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#runserver)

- `django-admin runserver [addrport]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-runserver)

  

在本地机器上启动一个轻量级的开发网络服务器。默认情况下，该服务器在 IP 地址 127.0.0.1 的 8000 端口上运行。你可以明确地传递一个 IP 地址和端口号。

如果你以普通用户的身份运行这个脚本 (推荐)， 你可能无法在低端口号上启动一个端口。低端口号是为超级用户（root）保留的。

本服务器使用 [`WSGI_APPLICATION`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-WSGI_APPLICATION) 配置中指定的 WSGI 应用对象。

警告

DO NOT USE THIS SERVER IN A PRODUCTION SETTING.

This lightweight development server has not gone through security audits or performance tests, hence is unsuitable for production. Making this server able to handle a production environment is outside the scope of Django.

开发服务器会根据需要为每个请求自动重新加载 Python 代码。你不需要重新启动服务器以使代码更改生效。然而，有些操作，如添加文件不会触发重启，所以在这些情况下你必须重启服务器。

如果你在 Linux 或 MacOS 上安装了 [pywatchman](https://pypi.org/project/pywatchman/) 和 [Watchman](https://facebook.github.io/watchman/) 服务，那么将使用内核信号来自动重新加载服务器（而不是每秒轮询文件修改时间戳）。这在大型项目上提供了更好的性能，在代码更改后减少了响应时间，更强大的更改检测以及减少了电力消耗。Django 支持 `pywatchman` 1.2.0 及更高版本。

有许多文件的大目录可能会导致性能问题。

当使用 Watchman 时，如果项目中包含大的非 Python 目录，比如 `node_modules`，建议忽略这个目录以获得最佳性能。关于如何做到这一点，请参见 [watchman文档](https://facebook.github.io/watchman/docs/config#ignore_dirs) 。

Watchman 超时

- `DJANGO_WATCHMAN_TIMEOUT`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#envvar-DJANGO_WATCHMAN_TIMEOUT)

  

`Watchman` 客户端的默认超时时间是 5 秒。你可以通过设置 [`DJANGO_WATCHMAN_TIMEOUT`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#envvar-DJANGO_WATCHMAN_TIMEOUT) 环境变量来改变它。

当你启动服务器时，以及每次在服务器运行时修改 Python 代码时，系统检查框架会检查你的整个 Django 项目是否存在一些常见的错误（参见 [`check`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-check) 命令）。如果发现任何错误，它们将被打印到标准输出。 你可以使用 `--skip-checks` 选项来跳过运行系统检查。

您可以根据需要运行任意数量的并发服务器,只要它们通过多次执行 django-admin runserver 运行在不同的端口上即可。

请注意，默认 IP 地址 `127.0.0.1` 无法从您网络上的其他计算机访问。为了使您的开发服务器对网络上的其他机器可见，请使用它自己的 IP 地址（例如 `192.168.2.1`）、`0``（``0.0.0.0` 的快捷方式）、`0.0. 0.0` 或 [``](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#id1)::[``](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#id3)（启用 IPv6）。

你可以提供一个用括号包围的 IPv6 地址（例如 `[200a::1]:8000`）。这将自动启用 IPv6 支持。

也可以使用只包含 ASCII 字符的主机名。

如果 [staticfiles](https://docs.djangoproject.com/zh-hans/5.1/ref/contrib/staticfiles/) contrib 应用被启用（新项目中的默认值）， [`runserver`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-runserver) 命令将被它自己的 [runserver](https://docs.djangoproject.com/zh-hans/5.1/ref/contrib/staticfiles/#staticfiles-runserver) 命令覆盖。

服务器的每个请求和响应的日志都会被发送到 [django.server](https://docs.djangoproject.com/zh-hans/5.1/ref/logging/#django-server-logger) 日志器。

- `--noreload```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-runserver-noreload)

  

禁用自动加载器。这意味着，如果特定的 Python 模块已经被加载到内存中，那么你在服务器运行时所做的任何 Python 代码更改将 *不会* 生效。

- `--nothreading```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-runserver-nothreading)

  

在开发服务器中禁止使用线程。默认情况下，服务器是多线程的。

- `--ipv6````, ``-6```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-runserver-ipv6)

  

开发服务器使用 IPv6。这将默认 IP 地址从 `127.0.0.1` 改为 `::1`。



#### 使用不同端口和地址的例子[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#examples-of-using-different-ports-and-addresses)

IP 地址 `127.0.0.1` 上的端口 8000：

```
django-admin runserver
```

IP 地址 `1.2.3.4` 上的端口 8000：

```
django-admin runserver 1.2.3.4:8000
```

IP 地址 `127.0.0.1` 上的端口 7000：

```
django-admin runserver 7000
```

IP 地址 `1.2.3.4` 上的端口 7000：

```
django-admin runserver 1.2.3.4:7000
```

IPv6 地址 `::1` 上的端口 8000：

```
django-admin runserver -6
```

IPv6 地址 `::1` 上的端口 7000：

```
django-admin runserver -6 7000
```

IPv6 地址 `2001:0db8:1234:5678::9` 上的端口 7000：

```
django-admin runserver [2001:0db8:1234:5678::9]:7000
```

主机 `localhost` 的 IPv4 地址上的端口 8000：

```
django-admin runserver localhost:8000
```

主机 `localhost` 的 IPv6 地址上的端口 8000：

```
django-admin runserver -6 localhost:8000
```



#### 用开发服务器服务静态文件[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#serving-static-files-with-the-development-server)

默认情况下，开发服务器不会为你的网站提供任何静态文件（比如 CSS 文件、图片、 [`MEDIA_URL`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-MEDIA_URL) 下的东西等等）。如果你想设置 Django 为服务静态媒体，请阅读 [如何管理静态文件（如图片、JavaScript、CSS）](https://docs.djangoproject.com/zh-hans/5.1/howto/static-files/)。



#### 在开发中使用 ASGI 进行服务[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#serving-with-asgi-in-development)

Django 的 `runserver` 命令提供了一个 WSGI 服务器。要在 ASGI 下运行，您需要使用一个 [ASGI 服务器](https://docs.djangoproject.com/zh-hans/5.1/howto/deployment/asgi/)。Django Daphne 项目提供了 [与 runserver 集成](https://docs.djangoproject.com/zh-hans/5.1/howto/deployment/asgi/daphne/#daphne-runserver)，您可以使用它。



### `sendtestemail`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#sendtestemail)

- `django-admin sendtestemail [email [email ...]]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-sendtestemail)

  

发送一个测试邮件（以确认通过 Django 发送邮件是否正常工作）给指定的收件人。例如：

```
django-admin sendtestemail foo@example.com bar@example.com
```

有几个选项，你可以将它们任意组合在一起使用：

- `--managers```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-sendtestemail-managers)

  

使用 [`mail_managers()`](https://docs.djangoproject.com/zh-hans/5.1/topics/email/#django.core.mail.mail_managers) 向 [`MANAGERS`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-MANAGERS) 中指定的邮件地址发送邮件。

- `--admins```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-sendtestemail-admins)

  

使用 [`mail_admins()`](https://docs.djangoproject.com/zh-hans/5.1/topics/email/#django.core.mail.mail_admins) 向 [`ADMINS`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-ADMINS) 中指定的邮件地址发送邮件。



### `shell`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#shell)

- `django-admin shell`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-shell)

  

启动 Python 交互式解释器。

- `--interface`` {ipython,bpython,python}``, ``-i`` {ipython,bpython,python}`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-shell-interface)

  

指定要使用的命令行。默认情况下，Django 会使用安装了的 [IPython](https://ipython.org/) 或 [bpython](https://bpython-interpreter.org/) 。如果两者都安装了，请指定使用哪一个：

IPython:

```
django-admin shell -i ipython
```

bpython:

```
django-admin shell -i bpython
```

如果你安装了一个 "rich" shell 但想要强制使用 "plain" Python 解释器，请使用 `python` 作为接口名称，如下所示：

```
django-admin shell -i python
```

- `--no-startup```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-shell-no-startup)

  

禁止读取“纯" Python 解释器的启动脚本。默认情况下，读取 [`PYTHONSTARTUP`](https://docs.python.org/3/using/cmdline.html#envvar-PYTHONSTARTUP) 环境变量或 `~/.pythonrc.py` 脚本所指向的脚本。

- `--command`` COMMAND``, ``-c`` COMMAND`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-shell-command)

  

允许你将一个命令作为字符串传递，以便像这样作为 Django 执行它：

```
django-admin shell --command="import django; print(django.__version__)"
```

你也可以在标准输入上传入代码来执行。例如：

```
$ django-admin shell <<EOF
> import django
> print(django.__version__)
> EOF
```

在 Windows 上，由于 [`select.select()`](https://docs.python.org/3/library/select.html#select.select) 在该平台上的实现限制，REPL 被输出。



### `showmigrations`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#showmigrations)

- `django-admin showmigrations [app_label [app_label ...]]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-showmigrations)

  

显示一个项目中的所有迁移。你可以从两种格式中选择一种：

- `--list````, ``-l```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-showmigrations-list)

  

列出 Django 知道的所有应用，每个应用的可用迁移，以及每个迁移是否被应用（在迁移名称旁边用 `[X]` 标记）。对于 `--verbosity` 2 及以上的应用，也会显示应用的日期时间。

没有迁移的应用程序也在列表中，但下面印有 `(no migrations)`。

这是默认的输出格式。

- `--plan````, ``-p```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-showmigrations-plan)

  

显示 Django 将遵循的迁移计划。和 `--list` 一样，应用的迁移也用 `[X]` 标记。对于 `--verbosity` 2 以上，也会显示迁移的所有依赖关系。

`app_label` 参数限制了输出，但是，所提供的应用的依赖也可能被包括在内。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-showmigrations-database)

  

指定要检查的数据库。默认为 `default`。



### `sqlflush`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#sqlflush)

- `django-admin sqlflush`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-sqlflush)

  

打印 [`flush`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-flush) 命令执行的 SQL 语句。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-sqlflush-database)

  

指定要打印 SQL 的数据库。默认值为 `default`。



### `sqlmigrate`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#sqlmigrate)

- `django-admin sqlmigrate app_label migration_name`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-sqlmigrate)

  

打印指定迁移的 SQL。这需要一个活动的数据库连接，它将用来解析约束名；这意味着你必须针对你希望以后应用它的数据库副本生成 SQL。

请注意，`sqlmigrate` 不会对其输出进行着色。

- `--backwards```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-sqlmigrate-backwards)

  

生成用于解除应用迁移的 SQL。默认情况下，所创建的 SQL 是用于向前运行迁移。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-sqlmigrate-database)

  

指定要生成 SQL 的数据库。默认值为 `default`。



### `sqlsequencereset`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#sqlsequencereset)

- `django-admin sqlsequencereset app_label [app_label ...]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-sqlsequencereset)

  

打印用于重置给定应用名称序列的 SQL 语句。

序列是一些数据库引擎用来跟踪自动递增字段的下一个可用数字的索引。

使用此命令生成 SQL，它将修复序列与其自动递增的字段数据不同步的情况。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-sqlsequencereset-database)

  

指定要打印 SQL 的数据库。默认值为 `default`。



### `squashmigrations`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#squashmigrations)

- `django-admin squashmigrations app_label [start_migration_name] migration_name`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-squashmigrations)

  

如果可能的话，将 `app_label` 的迁移（包括 `migration_name`）压缩成较少的迁移。压制后的迁移可以和未压制的迁移安全地并存。更多信息，请阅读 [压缩迁移](https://docs.djangoproject.com/zh-hans/5.1/topics/migrations/#migration-squashing)。

当给定 `start_migration_name` 时，Django 将只包含从这个迁移开始的迁移。这有助于减少 [`RunPython`](https://docs.djangoproject.com/zh-hans/5.1/ref/migration-operations/#django.db.migrations.operations.RunPython) 和 [`django.db.migrations.operations.RunSQL`](https://docs.djangoproject.com/zh-hans/5.1/ref/migration-operations/#django.db.migrations.operations.RunSQL) 迁移操作的限制。

- `--no-optimize```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-squashmigrations-no-optimize)

  

当生成一个压缩的迁移时，禁用优化器。默认情况下，Django 会尝试优化迁移中的操作，以减少生成文件的大小。如果这个过程失败或创建不正确的迁移，请使用这个选项，不过也请提交一个 Django 的 bug 报告来说明这个行为，因为优化的目的是为了安全。

- `--noinput````, ``--no-input```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-squashmigrations-noinput)

  

禁止所有的用户提示。

- `--squashed-name`` SQUASHED_NAME`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-squashmigrations-squashed-name)

  

设置被压缩的迁移的名称。当省略时，名称以第一次和最后一次迁移为基础，中间为 `_squashed_`。

- `--no-header```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-squashmigrations-no-header)

  

生成没有 Django 版本和时间戳头的压缩迁移文件。



### `startapp`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#startapp)

- `django-admin startapp name [directory]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-startapp)

  

在当前目录或给定的目标目录中为给定的应用名创建一个 Django 应用目录结构。

默认情况下，[新目录](https://github.com/django/django/blob/main/django/conf/app_template) 包含 `models.py` 文件和其他应用模板文件。如果只给出应用名称，应用目录将被创建在当前工作目录下。

如果提供了可选的目的地，Django 将使用现有的目录，而不是创建一个新的目录。你可以使用 '.' 来表示当前的工作目录。

例如：

```
django-admin startapp myapp /Users/jezdez/Code/myapp
```



- `--template`` TEMPLATE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-startapp-template)

  

提供指向自定义应用模板文件目录的路径，或指向未压缩档案（`.tar`）或压缩档案（`. tar.gz`、`.tar.bz2`、`.tar.xz`、`.tar.lzma`、`.tgz`、`.tbz2`、`.txz`、`.tlz`、`.zip`）的路径。

例如，当创建 `myapp` 应用程序时，这将在给定目录中查找一个应用程序模板：

```
django-admin startapp --template=/Users/jezdez/Code/my_app_template myapp
```

Django 还将接受 URL（`http`、`https`、`ftp`）与应用模板文件一起压缩归档，即时下载并解压。

例如，利用 GitHub 提供的将存储库公开为 zip 文件的功能，你可以使用如下的 URL：

```
django-admin startapp --template=https://github.com/githubuser/django-app-template/archive/main.zip myapp
```

- `--extension`` EXTENSIONS``, ``-e`` EXTENSIONS`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-startapp-extension)

  

指定应用模板中的哪些文件扩展名应该用模板引擎渲染。默认值为 `py`。

- `--name`` FILES``, ``-n`` FILES`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-startapp-name)

  

指定应用模板中的哪些文件（除了那些匹配 `--extension` 的文件外）应该用模板引擎渲染。默认为空列表。

- `--exclude`` DIRECTORIES``, ``-x`` DIRECTORIES`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-startapp-exclude)

  

指定除了 `.git` 和 `__pycache__` 之外，应用模板中的哪些目录应该被排除。如果没有提供这个选项，名为 `__pycache__` 或以 `.` 开头的目录将被排除。

所有匹配文件使用的 [`template context`](https://docs.djangoproject.com/zh-hans/5.1/ref/templates/api/#django.template.Context) 是：

- 传递给 `startapp` 命令的任何选项（在命令行支持的选项中）
- `app_name` ——传递给命令的应用名称
- `app_directory` ——新创建的应用的完整路径
- `camel_case_app_name` ——驼峰大小写格式的应用名称
- `docs_version` ——文档的版本： `'dev'` 或 `'1.x'`。
- `django_version` ——Django 的版本，例如 `'2.0.3'`。

警告

当应用模板文件用 Django 模板引擎渲染时（默认是所有 `*.py` 文件），Django 也会替换掉所有包含的游离模板变量。例如，如果其中一个 Python 文件中包含了解释与模板渲染相关的特定功能的 docstring，可能会导致一个错误的例子。

为了解决这个问题，你可以使用 [`templatetag`](https://docs.djangoproject.com/zh-hans/5.1/ref/templates/builtins/#std-templatetag-templatetag) 模板标签来”转义“模板语法的各个部分。

此外，为了允许包含 Django 模板语言语法的 Python 模板文件，同时也为了防止打包系统试图对无效的 `*.py` 文件进行字节编译，以 `.py-tpl` 结尾的模板文件将改名为 `.py`。

警告

在使用自定义应用程序（或项目）模板之前，应始终审查其内容：这些模板定义了将成为项目一部分的代码，这意味着这些代码将被视为你安装的任何应用程序或自己编写的代码一样可信。此外，甚至渲染模板实际上是执行作为管理命令输入提供的代码。Django 模板语言可能会为系统提供广泛的访问权限，因此请确保你使用的任何自定义模板值得信任。



### `startproject`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#startproject)

- `django-admin startproject name [directory]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-startproject)

  

在当前目录或给定的目标目录中为给定的项目名称创建一个 Django 项目目录结构。

默认情况下，[新目录](https://github.com/django/django/blob/main/django/conf/project_template) 包含 `manage.py` 和一个项目包（包含 `settings.py` 和其他文件）。

如果只给出项目名称，则项目目录和项目包都将命名为 `<projectname>`，并在当前工作目录下创建项目目录。

如果提供了可选的目的地，Django 将使用该已有目录作为项目目录，并在其中创建 `manage.py` 和项目包。用 '.' 表示当前的工作目录。

例如：

```
django-admin startproject myproject /Users/jezdez/Code/myproject_repo
```

- `--template`` TEMPLATE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-startproject-template)

  

指定一个自定义项目模板的目录、文件路径或 URL。参见 [`startapp --template`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-startapp-template) 文档中的例子和用法。

- `--extension`` EXTENSIONS``, ``-e`` EXTENSIONS`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-startproject-extension)

  

指定项目模板中的哪些文件扩展名应该用模板引擎渲染。默认值为 `py`。

- `--name`` FILES``, ``-n`` FILES`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-startproject-name)

  

指定项目模板中的哪些文件（除了那些匹配 `--extension` 的文件外）应该用模板引擎渲染。默认为空列表。

- `--exclude`` DIRECTORIES``, ``-x`` DIRECTORIES`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-startproject-exclude)

  

指定除了 `.git` 和 `__pycache__` 之外，项目模板中哪些目录应该被排除。如果没有提供这个选项，名为 `__pycache__` 或以 `.` 开头的目录将被排除。

使用的 [`template context`](https://docs.djangoproject.com/zh-hans/5.1/ref/templates/api/#django.template.Context) 是：

- 传递给 `startproject` 命令的任何选项（在命令支持的选项中）
- `project_name` ——传给命令的项目名称
- `project_directory` ——新创建项目的完整路径
- `secret_key` —— [`SECRET_KEY`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-SECRET_KEY) 设置的随机密钥
- `docs_version` ——文档的版本： `'dev'` 或 `'1.x'`。
- `django_version` ——Django 的版本，例如 `'2.0.3'`。

请参阅与 [`startapp`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-startapp) 中提到的 [渲染警告](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#render-warning) 和 [可信代码警告](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#trusted-code-warning) 相关的内容。



### `test`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#test)

- `django-admin test [test_label [test_label ...]]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-test)

  

为所有安装的应用运行测试。参见 [Django 中的测试](https://docs.djangoproject.com/zh-hans/5.1/topics/testing/) 获取更多信息。

- `--failfast```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-failfast)

  

测试失败后立即停止运行测试并报告失败。

- `--testrunner`` TESTRUNNER`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-testrunner)

  

控制用于执行测试的测试运行器类。这个值会覆盖 [`TEST_RUNNER`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-TEST_RUNNER) 配置提供的值。

- `--noinput````, ``--no-input```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-noinput)

  

压制所有用户提示。一个典型的提示是关于删除现有测试数据库的警告。



#### 测试运行器选项[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#test-runner-options)

`test` 命令代表指定的 [`--testrunner`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-testrunner) 接收选项。这些是默认测试运行器的选项： [`DiscoverRunner`](https://docs.djangoproject.com/zh-hans/5.1/topics/testing/advanced/#django.test.runner.DiscoverRunner)。

- `--keepdb```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-keepdb)

  

在测试运行之间保留测试数据库。这样做的好处是可以跳过创建和销毁这两个动作，从而大大缩短测试运行的时间，尤其是在大型测试套件中。如果测试数据库不存在，将在第一次运行时创建，然后在以后的每次运行中保留。除非 [`MIGRATE`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-TEST_MIGRATE) 测试配置为 `False`，否则任何未应用的迁移也会在运行测试套件之前应用到测试数据库。

- `--shuffle`` [SEED]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-shuffle)

  

在运行测试之前，随机化测试的顺序。这可以帮助发现那些没有正确隔离的测试。这个选项产生的测试顺序是给定的整数种子的一个确定性函数。当没有传递种子时，会随机选择一个种子并打印到控制台。要重复一个特定的测试顺序，需要传递一个种子。这个选项生成的测试顺序保留了 Django 的 [测试顺序的保证](https://docs.djangoproject.com/zh-hans/5.1/topics/testing/overview/#order-of-tests)。它们还保持了测试案例类别的测试分组。

在缩小隔离问题时，洗牌后的顺序也有一个特殊的一致性属性。也就是说，对于一个给定的种子，当运行一个测试子集时，新的顺序将是限制在较小的集合上的原始洗牌。同样地，在保持种子不变的情况下增加测试时，原始测试的顺序在新的顺序中也是一样的。

- `--reverse````, ``-r```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-reverse)

  

将测试案例以相反的执行顺序排序。这可能有助于调试没有正确隔离的测试的副作用。 [按测试类别分组](https://docs.djangoproject.com/zh-hans/5.1/topics/testing/overview/#order-of-tests) 在使用此选项时被保留下来。这可以和 `--shuffle` 一起使用，以扭转特定种子的顺序。

- `--debug-mode```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-debug-mode)

  

在运行测试之前，将 [`DEBUG`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-DEBUG) 设置为 `True`。这可能有助于解决测试失败的问题。

- `--debug-sql````, ``-d```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-debug-sql)

  

对失败的测试启用 [SQL 日志](https://docs.djangoproject.com/zh-hans/5.1/ref/logging/#django-db-logger)。如果 `--verbosity` 是 `2`，那么通过测试的查询也会被输出。

- `--parallel`` [N]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-parallel)

  

- `DJANGO_TEST_PROCESSES`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#envvar-DJANGO_TEST_PROCESSES)

  

在单独的并行进程中运行测试。由于现代处理器拥有多个内核，这使得运行测试的速度大大加快。

使用 `--parallel` 不加值，或使用 `auto` 值，根据 [`multiprocessing.cpu_count()`](https://docs.python.org/3/library/multiprocessing.html#multiprocessing.cpu_count) 每个核心运行一个测试进程。你可以通过传递所需的进程数来覆盖这一点，例如：`--parallel 4`，或者通过设置 [`DJANGO_TEST_PROCESSES`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#envvar-DJANGO_TEST_PROCESSES) 环境变量。

Django 将测试用例（[`unittest.TestCase`](https://docs.python.org/3/library/unittest.html#unittest.TestCase) 子类）分发给子进程。如果测试用例类的数量少于配置的进程数量，Django 将相应地减少进程数量。

每个进程都有自己的数据库。您必须确保不同的测试用例类不会访问相同的资源。例如，涉及文件系统的测试用例类应该为自己创建一个临时目录以供使用。

备注

如果你有不能并行运行的测试类，你可以使用 `SerializeMixin` 按顺序运行它们。参见 [强制按顺序运行测试类](https://docs.djangoproject.com/zh-hans/5.1/topics/testing/advanced/#topics-testing-enforce-run-sequentially)。

这个选项需要第三方的 `tlib` 包才能正确显示回溯。

```
$ python -m pip install tblib
```

这个功能在 Windows 上无法使用。它也不能与 Oracle 数据库后端一起工作。

如果你想在调试测试时使用 [`pdb`](https://docs.python.org/3/library/pdb.html#module-pdb)，你必须禁用并行执行（`-parallel=1`）。如果不这样做，你会看到类似 `bdb.BdbQuit` 的东西。

警告

当启用测试并行化后，测试失败时，Django 可能无法显示异常回溯。这可能会给调试带来困难。如果你遇到这个问题，请在不并行的情况下运行受影响的测试，以查看失败的回溯。

这是一个众所周知的限制。这是因为需要对对象进行序列化，以便在进程间进行交换。详见 [What can be pickled and unpickled?](https://docs.python.org/3/library/pickle.html#pickle-picklable)。

- `--tag`` TAGS`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-tag)

  

只运行 [特定标签标记](https://docs.djangoproject.com/zh-hans/5.1/topics/testing/tools/#topics-tagging-tests) 的测试。可多次指定，并与 `test --exclud-tag` 结合使用。

未能加载的测试总是被认为是匹配的。

- `--exclude-tag`` EXCLUDE_TAGS`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-exclude-tag)

  

不包括 [特定标签标记的](https://docs.djangoproject.com/zh-hans/5.1/topics/testing/tools/#topics-tagging-tests) 测试。可多次指定，并与 [`test --tag`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-tag) 结合使用。

- `-k`` TEST_NAME_PATTERNS`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-k)

  

运行与测试名称模式相匹配的测试方法和类，与 [`unittest's -k option`](https://docs.python.org/3/library/unittest.html#cmdoption-unittest-k) 一样。可以指定多次。

- `--pdb```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-pdb)

  

在每次测试错误或失败时，都会产生一个 `pdb` 调试器。如果你安装了 `ipdb`，则使用其代替。

- `--buffer````, ``-b```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-buffer)

  

丢弃通过测试的输出（`stdout` 和 `stderr`），与 [`unittest's --buffer option`](https://docs.python.org/3/library/unittest.html#cmdoption-unittest-b) 一样。

- `--no-faulthandler```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-no-faulthandler)

  

Django 在启动测试时自动调用 [`faulthandler.enable()`](https://docs.python.org/3/library/faulthandler.html#faulthandler.enable)，这允许它在解释器崩溃时打印一个回溯信息。传递 `--no-faulthandler` 来禁止这种行为。

- `--timing```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-timing)

  

输出时间，包括数据库设置和总运行时间。

- `--durations`` N`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-test-durations)

  

New in Django 5.0.

显示 N 个最慢的测试用例（当 N=0 时显示所有）。

Python 3.12 及以后版本

此功能仅适用于 Python 3.12 及更高版本。



### `testserver`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#testserver)

- `django-admin testserver [fixture [fixture ...]]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-testserver)

  

使用给定固定数据中的数据运行一个 Django 开发服务器（如 [`runserver`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-runserver)）。

例如，这个命令：

```
django-admin testserver mydata.json
```

...将执行以下步骤：

1. 按照 [测试数据库](https://docs.djangoproject.com/zh-hans/5.1/topics/testing/overview/#the-test-database) 中的描述，创建一个测试数据库。
2. 用给定固定数据的数据填充测试数据库。关于固定数据的更多信息，请参见上面的 [`loaddata`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-loaddata) 的文档）。
3. 运行 Django 开发服务器（如 [`runserver`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-runserver)），指向这个新创建的测试数据库，而不是你的生产数据库。

这在很多方面都很有用：

- 当你在写 [单元测试](https://docs.djangoproject.com/zh-hans/5.1/topics/testing/overview/) 时，你的视图如何与某些固定数据一起行动，你可以使用 `testserver` 在网络浏览器中手动与视图互动。
- 假设你正在开发你的 Django 应用程序，并且有一个“原始”的数据库副本，你想要与之交互。你可以将数据库转储为一个 [fixture](https://docs.djangoproject.com/zh-hans/5.1/topics/db/fixtures/#fixtures-explanation) （使用上面介绍的 [`dumpdata`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-dumpdata) 命令），然后使用 `testserver` 运行你的 Web 应用程序，并使用那些数据。通过这种安排，你可以灵活地对数据进行任何更改，因为你知道你所做的任何数据更改只会影响测试数据库。

请注意，这个服务器 *不会* 自动检测你的 Python 源代码的变化（就像 `srunerver` 那样)。但是，它可以检测到对模板的更改。

- `--addrport`` ADDRPORT`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-testserver-addrport)

  

指定与默认的 `127.0.0.1:8000` 不同的端口或 IP 地址和端口。这个值的格式和作用与 [`runserver`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-runserver) 命令的参数完全相同。

举例：

要在端口 7000 上运行测试服务器并使用 `fixture1` 和 `fixture2`：

```
django-admin testserver --addrport 7000 fixture1 fixture2
django-admin testserver fixture1 fixture2 --addrport 7000
```

（上面的语句是等价的。我们把这两句话都包括在内，是为了证明选项是在固定数据参数之前还是之后并不重要。）

要在 1.2.3.4:7000 上运行，并使用 `test` fixture：

```
django-admin testserver --addrport 1.2.3.4:7000 test
```

- `--noinput````, ``--no-input```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-testserver-noinput)

  

压制所有用户提示。一个典型的提示是关于删除现有测试数据库的警告。



## 应用程序提供的命令[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#commands-provided-by-applications)

有些命令只有 [`enabled`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-INSTALLED_APPS) [实现](https://docs.djangoproject.com/zh-hans/5.1/howto/custom-management-commands/) 它们的 `django.contrib` 应用程序时才可用。本节将按照应用来介绍这些命令。



### `django.contrib.auth`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-contrib-auth)



#### `changepassword`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#changepassword)

- `django-admin changepassword [<username>]`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-changepassword)

  

只有安装了 Django 的 [认证系统](https://docs.djangoproject.com/zh-hans/5.1/topics/auth/) （`django.contrib.auth`），这个命令才有效。

允许更改用户的密码。它提示你为给定的用户输入两次新密码。如果输入的密码相同，则立即成为新密码。如果你没有提供用户，命令将尝试更改与当前用户用户名匹配的密码。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-changepassword-database)

  

指定要为用户查询的数据库。默认为 `default`。

使用实例：

```
django-admin changepassword ringo
```



#### `createsuperuser`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#createsuperuser)

- `django-admin createsuperuser`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-createsuperuser)

  

- `DJANGO_SUPERUSER_PASSWORD`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#envvar-DJANGO_SUPERUSER_PASSWORD)

  

只有安装了 Django 的 [认证系统](https://docs.djangoproject.com/zh-hans/5.1/topics/auth/) （`django.contrib.auth`），这个命令才有效。

创建一个超级用户账户（拥有所有权限的用户）。如果你需要创建一个初始的超级用户账户，或者你需要为你的网站程序化地生成超级用户账户，这很有用。

当交互式运行时，该命令将提示为新的超级用户账户提供密码。非交互式运行时，可以通过设置 [`DJANGO_SUPERUSER_PASSWORD`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#envvar-DJANGO_SUPERUSER_PASSWORD) 环境变量提供密码。否则，将不设置密码，超级用户账户将无法登录，直到手动为其设置密码。

在非交互模式下， [`USERNAME_FIELD`](https://docs.djangoproject.com/zh-hans/5.1/topics/auth/customizing/#django.contrib.auth.models.CustomUser.USERNAME_FIELD) 和必填字段（列在 [`REQUIRED_FIELDS`](https://docs.djangoproject.com/zh-hans/5.1/topics/auth/customizing/#django.contrib.auth.models.CustomUser.REQUIRED_FIELDS) 中）回落到 `DJANGO_SUPERUSER_<uppercase_field_name>` 环境变量，除非它们被命令行参数覆盖。例如，要提供一个 `email` 字段，你可以使用 `DJANGO_SUPERUSER_EMAIL` 环境变量。

- `--noinput````, ``--no-input```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-createsuperuser-noinput)

  

压制所有用户提示。如果被抑制的提示不能自动解决，命令将以 error code 1 退出。

- `--username`` USERNAME`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-createsuperuser-username)

  

- `--email`` EMAIL`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-createsuperuser-email)

  

新账户的用户名和电子邮件地址可以通过使用命令行中的 `--username` 和 `--email` 参数来提供。如果没有提供这两个参数中的任何一个，`createsuperuser` 将在交互式运行时提示输入。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-createsuperuser-database)

  

指定保存超级用户对象的数据库。

如果你想自定义数据输入和验证，可以子类管理命令，并覆盖 `get_input_data()`。关于现有的实现和方法的参数，请查阅源代码。例如，如果你在 [`REQUIRED_FIELDS`](https://docs.djangoproject.com/zh-hans/5.1/topics/auth/customizing/#django.contrib.auth.models.CustomUser.REQUIRED_FIELDS) 中有一个 `ForeignKey`，并且希望允许创建一个实例，而不是输入现有实例的主键，这可能是有用的。



### `django.contrib.contenttypes`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-contrib-contenttypes)



#### `remove_stale_contenttypes`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#remove-stale-contenttypes)

- `django-admin remove_stale_contenttypes`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-remove_stale_contenttypes)

  

只有安装了 Django 的 [contenttypes 应用](https://docs.djangoproject.com/zh-hans/5.1/ref/contrib/contenttypes/) （ [`django.contrib.contenttypes`](https://docs.djangoproject.com/zh-hans/5.1/ref/contrib/contenttypes/#module-django.contrib.contenttypes)），这个命令才有效。

删除数据库中陈旧的内容类型（来自已删除的模型）。依赖于已删除内容类型的任何对象也将被删除。在你确认可以继续删除之前，将显示一个已删除对象的列表。

- `--database`` DATABASE`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-remove_stale_contenttypes-database)

  

指定要使用的数据库。默认为 `default`。

- `--include-stale-apps```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-remove_stale_contenttypes-include-stale-apps)

  

删除陈旧的内容类型，包括以前安装的应用程序的内容类型，这些内容类型已经从 [`INSTALLED_APPS`](https://docs.djangoproject.com/zh-hans/5.1/ref/settings/#std-setting-INSTALLED_APPS) 中删除。默认值为 `False`。



### `django.contrib.gis`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-contrib-gis)



#### `ogrinspect`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#ogrinspect)

只有在安装了 [GeoDjango](https://docs.djangoproject.com/zh-hans/5.1/ref/contrib/gis/) （`django.contrib.gis`）的情况下，该命令才可用。

请参考 GeoDjango 文档中它的 [`描述`](https://docs.djangoproject.com/zh-hans/5.1/ref/contrib/gis/commands/#django-admin-ogrinspect)。



### `django.contrib.sessions`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-contrib-sessions)



#### `clearsessions`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#clearsessions)

- `django-admin clearsessions`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-clearsessions)

  

可以以定时任务的形式运行，也可以直接清理过期会话。



### `django.contrib.staticfiles`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-contrib-staticfiles)



#### `collectstatic`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#collectstatic)

只有安装了 [静态文件应用程序](https://docs.djangoproject.com/zh-hans/5.1/howto/static-files/) （`django.contrib.staticfiles`），该命令才可用。

请参考它的 [静态文件](https://docs.djangoproject.com/zh-hans/5.1/ref/contrib/staticfiles/) 文档中的 [`描述`](https://docs.djangoproject.com/zh-hans/5.1/ref/contrib/staticfiles/#django-admin-collectstatic)。



#### `findstatic`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#findstatic)

只有安装了 [静态文件应用程序](https://docs.djangoproject.com/zh-hans/5.1/howto/static-files/) （`django.contrib.staticfiles`），该命令才可用。

请参考它的 [静态文件](https://docs.djangoproject.com/zh-hans/5.1/ref/contrib/staticfiles/) 文档中的 [`描述`](https://docs.djangoproject.com/zh-hans/5.1/ref/contrib/staticfiles/#django-admin-findstatic)。



## 默认选项[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#default-options)

尽管有些命令可能允许自己的自定义选项，但每个命令都默认允许以下选项：

- `--pythonpath`` PYTHONPATH`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-pythonpath)

  

将给定的文件系统路径添加到 Python [`sys.path`](https://docs.python.org/3/library/sys.html#sys.path) 模块属性中。如果没有提供，`django-admin` 将使用 [`PYTHONPATH`](https://docs.python.org/3/using/cmdline.html#envvar-PYTHONPATH) 环境变量。

这个选项在 `manage.py` 中是不必要的，因为它为你设置了 Python 路径。

使用实例：

```
django-admin migrate --pythonpath='/home/djangoprojects/myproject'
```

- `--settings`` SETTINGS`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-settings)

  

指定要使用的配置模块。配置模块应该使用 Python 包语法，例如 `mysite.settings`。如果没有提供，`django-admin` 将使用 [`DJANGO_SETTINGS_MODULE`](https://docs.djangoproject.com/zh-hans/5.1/topics/settings/#envvar-DJANGO_SETTINGS_MODULE) 环境变量。

这个选项在 `manage.py` 中是不必要的，因为它默认使用当前项目中的 `settings.py`。

使用实例：

```
django-admin migrate --settings=mysite.settings
```

- `--traceback```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-traceback)

  

当发生 [`CommandError`](https://docs.djangoproject.com/zh-hans/5.1/howto/custom-management-commands/#django.core.management.CommandError) 时，显示完整的堆栈跟踪。默认情况下，`django-admin` 将在发生 `CommandError` 时显示一个错误信息，并对任何其他异常显示一个完整的堆栈跟踪。

这个选项被 [`runserver`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-runserver) 忽略。

使用实例：

```
django-admin migrate --traceback
```

- `--verbosity`` {0,1,2,3}``, ``-v`` {0,1,2,3}`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-verbosity)

  

指定命令应打印到控制台的通知和调试信息的数量。

- `0` 表示没有输出。
- `1` 表示正常输出（默认）。
- `2` 表示详细输出。
- `3` 表示 *非常* 详细输出。

这个选项被 [`runserver`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-runserver) 忽略。

使用实例：

```
django-admin migrate --verbosity 2
```

- `--no-color```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-no-color)

  

禁用彩色化的命令输出。 有些命令会将其输出格式化为彩色。例如，错误将以红色打印到控制台，SQL 语句将以语法高亮显示。

使用实例：

```
django-admin runserver --no-color
```

- `--force-color```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-force-color)

  

强制对命令输出进行着色，如果不这样做的话，就会像 [语法着色](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#syntax-coloring) 中所讨论的那样被禁用。例如，你可能希望将彩色输出管道到另一个命令。

- `--skip-checks```[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-skip-checks)

  

在运行命令之前，跳过运行系统检查。这个选项只有在 `required_system_checks` 命令属性不是一个空列表或元组时才可用。

使用实例：

```
django-admin migrate --skip-checks
```



## 额外的细节[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#extra-niceties)



### 语法着色[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#syntax-coloring)

- `DJANGO_COLORS`[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#envvar-DJANGO_COLORS)

  

`django-admin` ／ `manage.py` 命令会使用漂亮的彩色编码输出，如果你的终端支持 ANSI 彩色输出的话。如果你把命令的输出用管道传送到另一个程序，它不会使用颜色代码，除非使用 [`--force-color`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#cmdoption-force-color) 选项。



#### Windows 支持[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#windows-support)

在 Windows 10 上，[Windows Terminal](https://www.microsoft.com/en-us/p/windows-terminal-preview/9n0dx20hk701) 应用程序、[VS Code](https://code.visualstudio.com/) 和 PowerShell（启用虚拟终端处理）允许彩色输出，并且默认支持。

在 Windows 下，传统的 `cmd.exe` 本地控制台不支持 ANSI 转义序列，所以默认情况下没有彩色输出。在这种情况下，需要两个第三方库中的一个：

- 安装 [colorama](https://pypi.org/project/colorama/)，这是一个将 ANSI 颜色代码转化为 Windows API 调用的 Python 包。Django 命令会检测到其存在，并将使用其服务来使输出具有类似 Unix 平台上的颜色效果。可以使用 pip 安装 `colorama`：

  ```
  ...\> py -m pip install "colorama >= 0.4.6"
  ```

- 安装 [ANSICON](http://adoxa.altervista.org/ansicon/) ，一个第三方工具，允许 `cmd.exe` 处理 ANSI 颜色代码。Django 命令会检测到它的存在，并利用它的服务为输出着色，就像在基于 Unix 的平台上一样。

在 Windows 上的其他现代终端环境，支持终端颜色，但没有被 Django 自动检测到，可以通过设置适当的环境变量 `ANSICON="on"` 来 “假装” 安装 `ANSICON`。



#### 自定义颜色[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#custom-colors)

语法高亮的颜色是可以自定义的。Django 有三种调色板：

- `dark`，适合在黑色背景上显示白色文字的终端。这是默认的调色板。
- `light`，适用于白底黑字的终端。
- `nocolor`，禁用语法高亮。

你可以通过设置 [`DJANGO_COLORS`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#envvar-DJANGO_COLORS) 环境变量来选择一个调色板，以指定你想要使用的调色板。例如，在 Unix 或 OS/X BASH shell 下指定 `light` 调色板，你可以在命令提示符下运行以下命令：

```
export DJANGO_COLORS="light"
```

你也可以自定义使用的颜色。Django 指定了一些使用颜色的角色：

- `error` - 一个重大错误。
- `notice` - 一个小错误。
- `success` - 成功。
- `warning` - 警告。
- `sql_field` - SQL 中模型字段的名称。
- `sql_coltype` - SQL 中模型字段的类型。
- `sql_keyword` - 一个 SQL 关键字。
- `sql_table` - SQL 中的模型名称。
- `http_info` - 一个 1XX 的 HTTP Informational 服务器响应。
- `http_success` - 一个 2XX 的 HTTP Success 服务器响应。
- `http_not_modified` - 一个 304 HTTP Not Modified 服务器响应。
- `http_redirect` - 一个除 304 之外的 3XX HTTP 重定向服务器响应。
- `http_not_found` - 一个 404 HTTP Not Found 服务器响应。
- `http_bad_request` - 一个除了 404 之外的 4XX HTTP Bad Request 服务器响应。
- `http_server_error` - 5XX HTTP 服务器错误响应。
- `migrate_heading` - 迁移管理命令中的标题。
- `migrate_label` - 迁移名称。

这些角色中的每一个都可以从以下列表中指定特定的前景和背景颜色：

- `black`
- `red`
- `green`
- `yellow`
- `blue`
- `magenta`
- `cyan`
- `white`

然后可以通过使用以下显示选项来修改这些颜色：

- `bold`
- `underscore`
- `blink`
- `reverse`
- `conceal`

颜色规格遵循以下模式之一：

- `role=fg`
- `role=fg/bg`
- `role=fg,option,option`
- `role=fg/bg,option,option`

其中，`role` 是有效颜色角色的名称，`fg` 是前景颜色，`bg` 是背景颜色，每个 `option` 都是颜色修改选项之一。多个颜色规范之间用分号分隔。例如：

```
export DJANGO_COLORS="error=yellow/blue,blink;notice=magenta"
```

将指定使用蓝色上闪烁的黄色显示错误，使用洋红色显示通知。所有其他颜色角色将不着色。

颜色也可以通过扩展基本调色板来指定。如果在颜色规范中放置了一个调色板名称，那么将加载由该调色板暗示的所有颜色。所以：

```
export DJANGO_COLORS="light;error=yellow/blue,blink;notice=magenta"
```

将指定使用浅色调色板中的所有颜色，*除了* 错误和通知的颜色，这些颜色将被覆盖。



### Bash 补全[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#bash-completion)

如果你使用 Bash shell，请考虑安装 Django bash 补全脚本，它位于 Django 源分发中的 [extras/django_bash_completion](https://github.com/django/django/blob/main/extras/django_bash_completion)。它启用了对 `django-admin` 和 `manage.py` 命令的制表符自动补全，这样你可以，例如...

- 输入 `django-admin`。
- 按 [TAB] 查看所有可用选项。
- 输入 `sql`，然后输入 [TAB]，查看所有名称以 `sql` 开头的可用选项。

关于如何添加自定义动作，请参见 [编写自定义 django-admin 命令](https://docs.djangoproject.com/zh-hans/5.1/howto/custom-management-commands/)。



### Black 格式化[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#black-formatting)

由 [`startproject`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-startproject)、[`startapp`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-startapp)、[`optimizemigration`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-optimizemigration)、[`makemigrations`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-makemigrations) 和 [`squashmigrations`](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django-admin-squashmigrations) 创建的 Python 文件将使用 `black` 命令进行格式化，如果它存在于你的 `PATH` 中的话。

如果你已经全局安装了 `black`，但不希望它用于当前项目，你可以显式设置 `PATH`：

```
PATH=path/to/venv/bin django-admin makemigrations
```

对于使用 `stdout` 的命令，如果需要，你可以将输出导向到 `black`：

```
django-admin inspectdb | black -
```



# 从你的代码中运行管理命令[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#running-management-commands-from-your-code)

- `django.core.management.``call_command`(*name*, **args*, ***options*)[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#django.core.management.call_command)

  

要从代码中调用管理命令，请使用 `call_command()`。

- `name`

  要调用的命令名称或命令对象的名称。除非测试时需要该对象，否则最好传递名称。

- `*args`

  命令接受的参数列表。参数被传递给参数解析器，所以你可以使用与命令行相同的风格。例如，`call_command('flush', '--verbosity=0')`。

- `**options`

  在命令行中接受的命名选项。选项传递给命令时不会触发参数解析器，这意味着你需要传递正确的类型。例如，`call_command('flush', verbosity=0)` （0 必须是一个整数而不是字符串）。

举例：

```
from django.core import management
from django.core.management.commands import loaddata

management.call_command("flush", verbosity=0, interactive=False)
management.call_command("loaddata", "test_data", verbosity=0)
management.call_command(loaddata.Command(), "test_data", verbosity=0)
```

需要注意的是，没有参数的命令选项是以关键字 `True` 或 `False` 的形式传递的，如上面的 `interactive` 选项。

命名的参数可以通过使用以下任何一种语法来传递：

```
# Similar to the command line
management.call_command("dumpdata", "--natural-foreign")

# Named argument similar to the command line minus the initial dashes and
# with internal dashes replaced by underscores
management.call_command("dumpdata", natural_foreign=True)

# `use_natural_foreign_keys` is the option destination variable
management.call_command("dumpdata", use_natural_foreign_keys=True)
```

当使用 `call_command()` 而不是 `django-admin` 或 `manage.py` 时，一些命令选项有不同的名称。例如，`django-admin createsuperuser --no-input` 翻译成 `call_command('creasuperuser', interactive=False)`。要找到 `call_command()` 的关键字参数名，请检查命令的源代码中传递给 `parser.add_argument()` 的 `dest` 参数。

取多个选项的命令选项会通过一个列表：

```
management.call_command("dumpdata", exclude=["contenttypes", "auth"])
```

`call_command()` 函数的返回值与命令的 `handle()` 方法的返回值相同。



## 输出重定向[¶](https://docs.djangoproject.com/zh-hans/5.1/ref/django-admin/#output-redirection)

注意，你可以重定向标准输出和错误流，因为所有命令都支持 `stdout` 和 `stderr` 选项。例如，你可以写：

```
with open("/path/to/command_output", "w") as f:
    management.call_command("dumpdata", stdout=f)
```