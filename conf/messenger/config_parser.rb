require 'json'

class ConfigParser

  def initialize(file_name)
    @file_name = file_name
  end
  
  attr_accessor :file_name

  def parse_json_config_file
    unless File.file?(file_name)
      abort(file_name + " not found in directory")
    end

    config_file_content = File.read(file_name)

    JSON.parse(config_file_content)
  end

  def get_requests_from_config(config)
    requests = config["requests"]

    unless requests
      abort("no requests found in " + @file_name)
    end

    requests
  end
end