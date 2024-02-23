create-migration: 
	@touch platform/conf/db/migration/default/V`date '+%Y%m%d%H%M%S'`__new_migration.sql