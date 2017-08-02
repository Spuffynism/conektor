require_relative 'config_parser'
require_relative 'requester'

$config_file_name = "config.json"

$url = ARGV[0]
$request_name = ARGV[1]

config_parser = ConfigParser.new $config_file_name

json_config = config_parser.parse_json_config_file
requests = config_parser.get_requests_from_config json_config
response = Requester.make_request requests[$request_name], $url

Requester.print_response response