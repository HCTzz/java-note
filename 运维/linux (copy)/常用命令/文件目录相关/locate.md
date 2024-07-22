**名称** 
        locate - 用名称找文件

**用法**
        locate [OPTION]... PATTERN...

**描述**
        locate 读取一个或多个由 updatedb 更新的数据库，并将至少与 PATTERN 之一匹配的文件名写入标准输出，每行一个。 如果未指定 --regex，则 PATTERN 可以包含通配符。 如果任何 PATTERN 不包含通配符，则 locate 的行为就像模式是 *PATTERN* 一  样。默认情况下， locate 不检查在数据库中找到的文件是否仍然存在（但如果数据库是使用 --require-visibility no 构建的，它确实要求所有父目录都存在）。locate 永远不会报告在相关数据库的最新更新之后创建的文件。

**退出状态**
        如果找到任何匹配项，或者使用 --limit 0、--help、--statistics 或 --version 选项之一调用 locate，则 locate 以状态 0 退出。 如果未找到匹配项或遇到致命错误，则 locate 以1 状态退出。读取数据库时遇到的错误不是致命错误，如果可能的话，将在其他指定的 数据库中继续搜索。

**选项**

```shell
    -A,--all  只打印与所有 PATTERN 匹配的条目，而不是只需要匹配其中一个。

    -b,--basename  仅将基本名称与指定模式匹配。这与--wholename 相反。

    -c,--count 将匹配的条目的数量写入标准输出，而不是文件名。

    -d,--database DBPATH  用 DBPATH 替换默认数据库。DBPATH 是一个:-分隔的数据库文件名列表。如果 			指定了多个 --database 选项，则生成的路径是单独路径的串联。空数据库文件名被默认数据库替换。			数据库文件名 - 指的是标准输入。请注意，数据库只能从标准输入读取一次。

    -e,--existing 仅打印运行 locate 时存在的文件的条目。

    -L,--follow 检查文件是否存在时（如果指定了 --existing 选项），后接软链接。这会导致从输                  出中省略损坏的软链接。这是默认行为。可以使用 --nofollow 指定相反的情况。

    -h,--help 输出帮助文档后退出。

    -i,--ignore-case  匹配模式时忽略大小写。

    -l,--limit,-n LIMIT 找到 LIMIT 个数的条目后成功退出。如果指定了 --count 选项，数量也被限定为                      LIMIT。

    -m,--mmap 忽略。与 BSD 和 GNU locate 兼容。

    -P,--nofollow,-H 在检查文件是否存在时（如果指定了 --existing 选项），后边不要接软链接。这会                  导致像其他文件一样地输出损坏的软链接。这是 --follow 的相反情况。

    -0,--null 在输出中使用 ASCII NUL 分隔条目，而不是每行一个条目。此选项旨在与 GNU                    xargs 的 --null 选项进行互操作。

    -S,--statistics 将每个读取数据库的统计信息写入标准输出，而不是搜索文件并成功退出。

    -q,--quiet 在读数据库和操作数据库时遇到的错误信息不输出。

    -r,--regexp REGEXP 搜索基本的正则表达式 REGEXP。 如果使用此选项，则不允许使用 PATTERN，                  但可以指定此选项多次。

    --regex 将所有 PATTERN 解释为扩展的正则表达式。

    -s,--stdio 忽略。与 BSD 和 GNU locate 兼容。

    -V,--version 输出版本信息后退出。

    -w,--wholename 仅将整个路径名与指定模式匹配。这是默认行为。可以使用 --basename 指定相反                  的内容。
```

**文件**
        默认搜索的数据库：/var/lib/mlocate/mlocate.db

**环境变量**
        LOCATE_PATH 附加数据库的路径，添加在默认数据库或使用 --database 选项指定的数据库之                      后。       

**注意事项**
        处理请求的数据库的顺序未指定，这允许 locate 出于安全原因重新排序数据库路径。            locate 尝试以该顺序与 slocate（没有用于创建数据库的选项）和 GNU locate 兼容。            这就是不切实际的默认使用 --follow 选项以及令人困惑的 --regex 和 --regexp 选项集的          原因。-r 选项的短拼写与 GNU locate 不兼容，它对应于 --regex 选项。使用长选项名            称以避免混淆。LOCATE_PATH 环境变量替换了 BSD 和 GNU locate 中的默认数据              库，但在此实现和 slocate 中它被添加到其他数据库中。
