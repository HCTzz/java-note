# InnoDB启动选项和系统变量

- 系统变量true或false可以在服务器启动时通过命名来启用，也可以使用`--skip-`前缀禁用 。例如，要启用或禁用`InnoDB`自适应哈希索引，可以 在命令行上使用 [`--innodb-adaptive-hash-index`](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_adaptive_hash_index)或 [`--skip-innodb-adaptive-hash-index`](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_adaptive_hash_index)， [`innodb_adaptive_hash_index`](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_adaptive_hash_index)或者 `skip_innodb_adaptive_hash_index`在选项文件中使用。
- 可以 在命令行或 选项文件中指定采用数字值的系统变量 。**--var_name=value var_name=value**
- 可以在运行时更改许多系统变量（请参见 [第5.1.8.2节“动态系统变量”](https://dev.mysql.com/doc/refman/5.6/en/dynamic-system-variables.html)）。
- 有关变量范围修饰符`GLOBAL`和 `SESSION`变量范围修饰符的信息，请参阅 [`SET`](https://dev.mysql.com/doc/refman/5.6/en/set-variable.html) 语句文档。
- 某些选项控制`InnoDB`数据文件的位置和布局 。 [第14.8.1节“ InnoDB启动配置”](https://dev.mysql.com/doc/refman/5.6/en/innodb-init-startup-configuration.html)介绍了如何使用这些选项。
- 最初可能不会使用的某些选项可帮助`InnoDB`根据计算机容量和数据库[工作负载](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_workload)调整 性能特征 。
- 有关指定选项和系统变量的更多信息，请参见[第4.2.2节“指定程序选项”](https://dev.mysql.com/doc/refman/5.6/en/program-options.html)。

### 表14.13 InnoDB选项和变量参考

| Name                                                         | Cmd-Line | Option File | System Var | Status Var | Var Scope | Dynamic |
| :----------------------------------------------------------- | :------- | :---------- | :--------- | :--------- | :-------- | :------ |
| [daemon_memcached_enable_binlog](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_daemon_memcached_enable_binlog) | Yes      | Yes         | Yes        |            | Global    | No      |
| [daemon_memcached_engine_lib_name](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_daemon_memcached_engine_lib_name) | Yes      | Yes         | Yes        |            | Global    | No      |
| [daemon_memcached_engine_lib_path](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_daemon_memcached_engine_lib_path) | Yes      | Yes         | Yes        |            | Global    | No      |
| [daemon_memcached_option](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_daemon_memcached_option) | Yes      | Yes         | Yes        |            | Global    | No      |
| [daemon_memcached_r_batch_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_daemon_memcached_r_batch_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [daemon_memcached_w_batch_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_daemon_memcached_w_batch_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [foreign_key_checks](https://dev.mysql.com/doc/refman/5.6/en/server-system-variables.html#sysvar_foreign_key_checks) |          |             | Yes        |            | Both      | Yes     |
| [ignore_builtin_innodb](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_ignore_builtin_innodb) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#option_mysqld_innodb) | Yes      | Yes         |            |            |           |         |
| [innodb_adaptive_flushing](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_adaptive_flushing) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_adaptive_flushing_lwm](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_adaptive_flushing_lwm) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_adaptive_hash_index](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_adaptive_hash_index) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_adaptive_max_sleep_delay](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_adaptive_max_sleep_delay) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_additional_mem_pool_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_additional_mem_pool_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_api_bk_commit_interval](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_api_bk_commit_interval) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_api_disable_rowlock](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_api_disable_rowlock) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_api_enable_binlog](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_api_enable_binlog) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_api_enable_mdl](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_api_enable_mdl) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_api_trx_level](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_api_trx_level) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_autoextend_increment](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_autoextend_increment) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_autoinc_lock_mode](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_autoinc_lock_mode) | Yes      | Yes         | Yes        |            | Global    | No      |
| [Innodb_available_undo_logs](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_available_undo_logs) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_bytes_data](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_bytes_data) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_bytes_dirty](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_bytes_dirty) |          |             |            | Yes        | Global    | No      |
| [innodb_buffer_pool_dump_at_shutdown](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_buffer_pool_dump_at_shutdown) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_buffer_pool_dump_now](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_buffer_pool_dump_now) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [Innodb_buffer_pool_dump_status](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_dump_status) |          |             |            | Yes        | Global    | No      |
| [innodb_buffer_pool_filename](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_buffer_pool_filename) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_buffer_pool_instances](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_buffer_pool_instances) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_buffer_pool_load_abort](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_buffer_pool_load_abort) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_buffer_pool_load_at_startup](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_buffer_pool_load_at_startup) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_buffer_pool_load_now](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_buffer_pool_load_now) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [Innodb_buffer_pool_load_status](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_load_status) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_pages_data](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_pages_data) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_pages_dirty](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_pages_dirty) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_pages_flushed](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_pages_flushed) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_pages_free](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_pages_free) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_pages_latched](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_pages_latched) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_pages_misc](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_pages_misc) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_pages_total](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_pages_total) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_read_ahead](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_read_ahead) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_read_ahead_evicted](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_read_ahead_evicted) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_read_ahead_rnd](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_read_ahead_rnd) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_read_requests](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_read_requests) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_reads](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_reads) |          |             |            | Yes        | Global    | No      |
| [innodb_buffer_pool_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_buffer_pool_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [Innodb_buffer_pool_wait_free](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_wait_free) |          |             |            | Yes        | Global    | No      |
| [Innodb_buffer_pool_write_requests](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_buffer_pool_write_requests) |          |             |            | Yes        | Global    | No      |
| [innodb_change_buffer_max_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_change_buffer_max_size) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_change_buffering](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_change_buffering) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_change_buffering_debug](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_change_buffering_debug) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_checksum_algorithm](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_checksum_algorithm) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_checksums](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_checksums) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_cmp_per_index_enabled](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_cmp_per_index_enabled) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_commit_concurrency](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_commit_concurrency) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_compression_failure_threshold_pct](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_compression_failure_threshold_pct) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_compression_level](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_compression_level) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_compression_pad_pct_max](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_compression_pad_pct_max) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_concurrency_tickets](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_concurrency_tickets) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_data_file_path](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_data_file_path) | Yes      | Yes         | Yes        |            | Global    | No      |
| [Innodb_data_fsyncs](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_data_fsyncs) |          |             |            | Yes        | Global    | No      |
| [innodb_data_home_dir](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_data_home_dir) | Yes      | Yes         | Yes        |            | Global    | No      |
| [Innodb_data_pending_fsyncs](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_data_pending_fsyncs) |          |             |            | Yes        | Global    | No      |
| [Innodb_data_pending_reads](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_data_pending_reads) |          |             |            | Yes        | Global    | No      |
| [Innodb_data_pending_writes](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_data_pending_writes) |          |             |            | Yes        | Global    | No      |
| [Innodb_data_read](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_data_read) |          |             |            | Yes        | Global    | No      |
| [Innodb_data_reads](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_data_reads) |          |             |            | Yes        | Global    | No      |
| [Innodb_data_writes](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_data_writes) |          |             |            | Yes        | Global    | No      |
| [Innodb_data_written](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_data_written) |          |             |            | Yes        | Global    | No      |
| [Innodb_dblwr_pages_written](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_dblwr_pages_written) |          |             |            | Yes        | Global    | No      |
| [Innodb_dblwr_writes](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_dblwr_writes) |          |             |            | Yes        | Global    | No      |
| [innodb_disable_sort_file_cache](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_disable_sort_file_cache) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_doublewrite](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_doublewrite) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_fast_shutdown](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_fast_shutdown) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_fil_make_page_dirty_debug](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_fil_make_page_dirty_debug) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_file_format](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_file_format) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_file_format_check](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_file_format_check) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_file_format_max](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_file_format_max) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_file_per_table](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_file_per_table) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_flush_log_at_timeout](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_flush_log_at_timeout) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_flush_log_at_trx_commit](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_flush_log_at_trx_commit) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_flush_method](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_flush_method) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_flush_neighbors](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_flush_neighbors) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_flushing_avg_loops](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_flushing_avg_loops) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_force_load_corrupted](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_force_load_corrupted) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_force_recovery](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_force_recovery) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_ft_aux_table](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_aux_table) |          |             | Yes        |            | Global    | Yes     |
| [innodb_ft_cache_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_cache_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_ft_enable_diag_print](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_enable_diag_print) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_ft_enable_stopword](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_enable_stopword) | Yes      | Yes         | Yes        |            | Both      | Yes     |
| [innodb_ft_max_token_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_max_token_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_ft_min_token_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_min_token_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_ft_num_word_optimize](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_num_word_optimize) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_ft_result_cache_limit](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_result_cache_limit) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_ft_server_stopword_table](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_server_stopword_table) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_ft_sort_pll_degree](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_sort_pll_degree) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_ft_total_cache_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_total_cache_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_ft_user_stopword_table](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_ft_user_stopword_table) | Yes      | Yes         | Yes        |            | Both      | Yes     |
| [Innodb_have_atomic_builtins](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_have_atomic_builtins) |          |             |            | Yes        | Global    | No      |
| [innodb_io_capacity](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_io_capacity) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_io_capacity_max](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_io_capacity_max) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_large_prefix](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_large_prefix) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_limit_optimistic_insert_debug](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_limit_optimistic_insert_debug) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_lock_wait_timeout](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_lock_wait_timeout) | Yes      | Yes         | Yes        |            | Both      | Yes     |
| [innodb_locks_unsafe_for_binlog](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_locks_unsafe_for_binlog) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_log_buffer_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_log_buffer_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_log_checkpoint_now](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_log_checkpoint_now) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_log_compressed_pages](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_log_compressed_pages) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_log_file_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_log_file_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_log_files_in_group](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_log_files_in_group) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_log_group_home_dir](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_log_group_home_dir) | Yes      | Yes         | Yes        |            | Global    | No      |
| [Innodb_log_waits](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_log_waits) |          |             |            | Yes        | Global    | No      |
| [Innodb_log_write_requests](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_log_write_requests) |          |             |            | Yes        | Global    | No      |
| [Innodb_log_writes](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_log_writes) |          |             |            | Yes        | Global    | No      |
| [innodb_lru_scan_depth](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_lru_scan_depth) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_max_dirty_pages_pct](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_max_dirty_pages_pct) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_max_dirty_pages_pct_lwm](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_max_dirty_pages_pct_lwm) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_max_purge_lag](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_max_purge_lag) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_max_purge_lag_delay](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_max_purge_lag_delay) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_mirrored_log_groups](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_mirrored_log_groups) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_monitor_disable](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_monitor_disable) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_monitor_enable](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_monitor_enable) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_monitor_reset](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_monitor_reset) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_monitor_reset_all](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_monitor_reset_all) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [Innodb_num_open_files](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_num_open_files) |          |             |            | Yes        | Global    | No      |
| [innodb_numa_interleave](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_numa_interleave) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_old_blocks_pct](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_old_blocks_pct) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_old_blocks_time](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_old_blocks_time) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_online_alter_log_max_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_online_alter_log_max_size) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_open_files](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_open_files) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_optimize_fulltext_only](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_optimize_fulltext_only) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [Innodb_os_log_fsyncs](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_os_log_fsyncs) |          |             |            | Yes        | Global    | No      |
| [Innodb_os_log_pending_fsyncs](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_os_log_pending_fsyncs) |          |             |            | Yes        | Global    | No      |
| [Innodb_os_log_pending_writes](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_os_log_pending_writes) |          |             |            | Yes        | Global    | No      |
| [Innodb_os_log_written](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_os_log_written) |          |             |            | Yes        | Global    | No      |
| [Innodb_page_size](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_page_size) |          |             |            | Yes        | Global    | No      |
| [innodb_page_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_page_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [Innodb_pages_created](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_pages_created) |          |             |            | Yes        | Global    | No      |
| [Innodb_pages_read](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_pages_read) |          |             |            | Yes        | Global    | No      |
| [Innodb_pages_written](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_pages_written) |          |             |            | Yes        | Global    | No      |
| [innodb_print_all_deadlocks](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_print_all_deadlocks) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_purge_batch_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_purge_batch_size) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_purge_threads](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_purge_threads) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_random_read_ahead](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_random_read_ahead) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_read_ahead_threshold](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_read_ahead_threshold) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_read_io_threads](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_read_io_threads) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_read_only](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_read_only) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_replication_delay](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_replication_delay) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_rollback_on_timeout](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_rollback_on_timeout) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_rollback_segments](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_rollback_segments) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [Innodb_row_lock_current_waits](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_row_lock_current_waits) |          |             |            | Yes        | Global    | No      |
| [Innodb_row_lock_time](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_row_lock_time) |          |             |            | Yes        | Global    | No      |
| [Innodb_row_lock_time_avg](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_row_lock_time_avg) |          |             |            | Yes        | Global    | No      |
| [Innodb_row_lock_time_max](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_row_lock_time_max) |          |             |            | Yes        | Global    | No      |
| [Innodb_row_lock_waits](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_row_lock_waits) |          |             |            | Yes        | Global    | No      |
| [Innodb_rows_deleted](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_rows_deleted) |          |             |            | Yes        | Global    | No      |
| [Innodb_rows_inserted](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_rows_inserted) |          |             |            | Yes        | Global    | No      |
| [Innodb_rows_read](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_rows_read) |          |             |            | Yes        | Global    | No      |
| [Innodb_rows_updated](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_rows_updated) |          |             |            | Yes        | Global    | No      |
| [innodb_saved_page_number_debug](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_saved_page_number_debug) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_sort_buffer_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_sort_buffer_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_spin_wait_delay](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_spin_wait_delay) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_stats_auto_recalc](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_stats_auto_recalc) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_stats_include_delete_marked](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_stats_include_delete_marked) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_stats_method](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_stats_method) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_stats_on_metadata](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_stats_on_metadata) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_stats_persistent](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_stats_persistent) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_stats_persistent_sample_pages](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_stats_persistent_sample_pages) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_stats_sample_pages](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_stats_sample_pages) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_stats_transient_sample_pages](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_stats_transient_sample_pages) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb-status-file](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#option_mysqld_innodb-status-file) | Yes      | Yes         |            |            |           |         |
| [innodb_status_output](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_status_output) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_status_output_locks](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_status_output_locks) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_strict_mode](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_strict_mode) | Yes      | Yes         | Yes        |            | Both      | Yes     |
| [innodb_support_xa](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_support_xa) | Yes      | Yes         | Yes        |            | Both      | Yes     |
| [innodb_sync_array_size](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_sync_array_size) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_sync_spin_loops](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_sync_spin_loops) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_table_locks](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_table_locks) | Yes      | Yes         | Yes        |            | Both      | Yes     |
| [innodb_thread_concurrency](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_thread_concurrency) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_thread_sleep_delay](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_thread_sleep_delay) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_tmpdir](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_tmpdir) | Yes      | Yes         | Yes        |            | Both      | Yes     |
| [Innodb_truncated_status_writes](https://dev.mysql.com/doc/refman/5.6/en/server-status-variables.html#statvar_Innodb_truncated_status_writes) |          |             |            | Yes        | Global    | No      |
| [innodb_trx_purge_view_update_only_debug](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_trx_purge_view_update_only_debug) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_trx_rseg_n_slots_debug](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_trx_rseg_n_slots_debug) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_undo_directory](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_undo_directory) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_undo_logs](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_undo_logs) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [innodb_undo_tablespaces](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_undo_tablespaces) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_use_native_aio](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_use_native_aio) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_use_sys_malloc](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_use_sys_malloc) | Yes      | Yes         | Yes        |            | Global    | No      |
| [innodb_version](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_version) |          |             | Yes        |            | Global    | No      |
| [innodb_write_io_threads](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_write_io_threads) | Yes      | Yes         | Yes        |            | Global    | No      |
| [timed_mutexes](https://dev.mysql.com/doc/refman/5.6/en/server-system-variables.html#sysvar_timed_mutexes) | Yes      | Yes         | Yes        |            | Global    | Yes     |
| [unique_checks](https://dev.mysql.com/doc/refman/5.6/en/server-system-variables.html#sysvar_unique_checks) |          |             | Yes        |            | Both      | Yes     |

### InnoDB命令选项

**[--innodb[=value]]**(https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#option_mysqld_innodb)

| 命令行格式 | `--innodb[=value]` |
| :--------- | ------------------ |
| 不推荐使用 | 5.6.21             |
| 类型       | 枚举               |
| 默认值     | `ON`               |
| 有效值     | `OFF``ON``FORCE`   |

`InnoDB`如果服务器是在`InnoDB`支持下编译的，则 控制存储引擎的 加载。此选项有三态格式，可能值`OFF`， `ON`或`FORCE`。请参见 [第5.5.1节“安装和卸载插件”](https://dev.mysql.com/doc/refman/5.6/en/plugin-loading.html)。

要禁用`InnoDB`，请使用 [`--innodb=OFF`](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#option_mysqld_innodb) 或 [`--skip-innodb`](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#option_mysqld_innodb)。在这种情况下，因为默认存储引擎为 [`InnoDB`](https://dev.mysql.com/doc/refman/5.6/en/innodb-storage-engine.html)，否则服务器将不会启动，除非您同时使用 [`--default-storage-engine`](https://dev.mysql.com/doc/refman/5.6/en/server-system-variables.html#sysvar_default_storage_engine)并将 [`--default-tmp-storage-engine`](https://dev.mysql.com/doc/refman/5.6/en/server-system-variables.html#sysvar_default_tmp_storage_engine)永久和`TEMPORARY`表的默认引擎设置为其他引擎 。

从MySQL 5.6.21开始， 不赞成使用选项， [`--innodb=OFF`](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#option_mysqld_innodb) 并且 [`--skip-innodb`](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#option_mysqld_innodb)使用它们会产生警告。这些选项将在将来的MySQL版本中删除。